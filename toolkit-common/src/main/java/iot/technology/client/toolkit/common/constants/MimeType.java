/*
 * Copyright © 2019-2025 The Toolkit Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package iot.technology.client.toolkit.common.constants;

/*
 * Copyright 2019-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import iot.technology.client.toolkit.common.utils.Assert;
import iot.technology.client.toolkit.common.utils.CollectionUtils;
import iot.technology.client.toolkit.common.utils.MimeTypeUtils;
import iot.technology.client.toolkit.common.utils.ObjectUtils;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Represents a MIME Type, as originally defined in RFC 2046 and subsequently
 * used in other Internet protocols including HTTP.
 *
 * <p>This class, however, does not contain support for the q-parameters used
 * in HTTP content negotiation. Those can be found in the subclass
 * {@code org.springframework.http.MediaType} in the {@code spring-web} module.
 *
 * <p>Consists of a {@linkplain #getType() type} and a {@linkplain #getSubtype() subtype}.
 * Also has functionality to parse MIME Type values from a {@code String} using
 *
 * @author Arjen Poutsma
 * @author Juergen Hoeller
 * @author Rossen Stoyanchev
 * @author Sam Brannen
 * @since 4.0
 */
public class MimeType implements Comparable<MimeType>, Serializable {

	private static final long serialVersionUID = 4085923477777865903L;


	public static final String WILDCARD_TYPE = "*";

	private static final String PARAM_CHARSET = "charset";

	private static final BitSet TOKEN;

	static {
		// variable names refer to RFC 2616, section 2.2
		BitSet ctl = new BitSet(128);
		for (int i = 0; i <= 31; i++) {
			ctl.set(i);
		}
		ctl.set(127);

		BitSet separators = new BitSet(128);
		separators.set('(');
		separators.set(')');
		separators.set('<');
		separators.set('>');
		separators.set('@');
		separators.set(',');
		separators.set(';');
		separators.set(':');
		separators.set('\\');
		separators.set('\"');
		separators.set('/');
		separators.set('[');
		separators.set(']');
		separators.set('?');
		separators.set('=');
		separators.set('{');
		separators.set('}');
		separators.set(' ');
		separators.set('\t');

		TOKEN = new BitSet(128);
		TOKEN.set(0, 128);
		TOKEN.andNot(ctl);
		TOKEN.andNot(separators);
	}


	private final String type;

	private final String subtype;

	private final Map<String, String> parameters;

	private volatile String toStringValue;

	/**
	 * Create a new {@code MimeType} for the given primary type and subtype.
	 * <p>The parameters are empty.
	 *
	 * @param type    the primary type
	 * @param subtype the subtype
	 * @throws IllegalArgumentException if any of the parameters contains illegal characters
	 */
	public MimeType(String type, String subtype) {
		this(type, subtype, Collections.emptyMap());
	}

	/**
	 * Create a new {@code MimeType} for the given type, subtype, and parameters.
	 *
	 * @param type       the primary type
	 * @param subtype    the subtype
	 * @param parameters the parameters (may be {@code null})
	 * @throws IllegalArgumentException if any of the parameters contains illegal characters
	 */
	public MimeType(String type, String subtype, Map<String, String> parameters) {
		Assert.hasLength(type, "'type' must not be empty");
		Assert.hasLength(subtype, "'subtype' must not be empty");
		checkToken(type);
		checkToken(subtype);
		this.type = type.toLowerCase(Locale.ENGLISH);
		this.subtype = subtype.toLowerCase(Locale.ENGLISH);
		if (!CollectionUtils.isEmpty(parameters)) {
			Map<String, String> map = new LinkedHashMap<>(parameters.size());
			parameters.forEach((attribute, value) -> {
				checkParameters(attribute, value);
				map.put(attribute, value);
			});
			this.parameters = Collections.unmodifiableMap(map);
		} else {
			this.parameters = Collections.emptyMap();
		}
	}

	/**
	 * Checks the given token string for illegal characters, as defined in RFC 2616,
	 * section 2.2.
	 *
	 * @throws IllegalArgumentException in case of illegal characters
	 * @see <a href="https://tools.ietf.org/html/rfc2616#section-2.2">HTTP 1.1, section 2.2</a>
	 */
	private void checkToken(String token) {
		for (int i = 0; i < token.length(); i++) {
			char ch = token.charAt(i);
			if (!TOKEN.get(ch)) {
				throw new IllegalArgumentException("Invalid token character '" + ch + "' in token \"" + token + "\"");
			}
		}
	}

	protected void checkParameters(String attribute, String value) {
		Assert.hasLength(attribute, "'attribute' must not be empty");
		Assert.hasLength(value, "'value' must not be empty");
		checkToken(attribute);
		if (PARAM_CHARSET.equals(attribute)) {
			value = unquote(value);
			Charset.forName(value);
		} else if (!isQuotedString(value)) {
			checkToken(value);
		}
	}

	private boolean isQuotedString(String s) {
		if (s.length() < 2) {
			return false;
		} else {
			return ((s.startsWith("\"") && s.endsWith("\"")) || (s.startsWith("'") && s.endsWith("'")));
		}
	}

	protected String unquote(String s) {
		return (isQuotedString(s) ? s.substring(1, s.length() - 1) : s);
	}

	/**
	 * Indicates whether the {@linkplain #getType() type} is the wildcard character
	 * <code>&#42;</code> or not.
	 */
	public boolean isWildcardType() {
		return WILDCARD_TYPE.equals(getType());
	}

	/**
	 * Indicates whether the {@linkplain #getSubtype() subtype} is the wildcard
	 * character <code>&#42;</code> or the wildcard character followed by a suffix
	 * (e.g. <code>&#42;+xml</code>).
	 *
	 * @return whether the subtype is a wildcard
	 */
	public boolean isWildcardSubtype() {
		return WILDCARD_TYPE.equals(getSubtype()) || getSubtype().startsWith("*+");
	}

	/**
	 * Return the primary type.
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Return the subtype.
	 */
	public String getSubtype() {
		return this.subtype;
	}

	/**
	 * Return the character set, as indicated by a {@code charset} parameter, if any.
	 *
	 * @return the character set, or {@code null} if not available
	 * @since 4.3
	 */
	public Charset getCharset() {
		String charset = getParameter(PARAM_CHARSET);
		return (charset != null ? Charset.forName(unquote(charset)) : null);
	}

	/**
	 * Return a generic parameter value, given a parameter name.
	 *
	 * @param name the parameter name
	 * @return the parameter value, or {@code null} if not present
	 */
	public String getParameter(String name) {
		return this.parameters.get(name);
	}

	/**
	 * Return all generic parameter values.
	 *
	 * @return a read-only map (possibly empty, never {@code null})
	 */
	public Map<String, String> getParameters() {
		return this.parameters;
	}


	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MimeType)) {
			return false;
		}
		MimeType otherType = (MimeType) other;
		return (this.type.equalsIgnoreCase(otherType.type) &&
				this.subtype.equalsIgnoreCase(otherType.subtype) &&
				parametersAreEqual(otherType));
	}

	/**
	 * Determine if the parameters in this {@code MimeType} and the supplied
	 * {@code MimeType} are equal, performing case-insensitive comparisons
	 * for {@link Charset Charsets}.
	 *
	 * @since 4.2
	 */
	private boolean parametersAreEqual(MimeType other) {
		if (this.parameters.size() != other.parameters.size()) {
			return false;
		}

		for (Map.Entry<String, String> entry : this.parameters.entrySet()) {
			String key = entry.getKey();
			if (!other.parameters.containsKey(key)) {
				return false;
			}
			if (PARAM_CHARSET.equals(key)) {
				if (!ObjectUtils.nullSafeEquals(getCharset(), other.getCharset())) {
					return false;
				}
			} else if (!ObjectUtils.nullSafeEquals(entry.getValue(), other.parameters.get(key))) {
				return false;
			}
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = this.type.hashCode();
		result = 31 * result + this.subtype.hashCode();
		result = 31 * result + this.parameters.hashCode();
		return result;
	}

	@Override
	public String toString() {
		String value = this.toStringValue;
		if (value == null) {
			StringBuilder builder = new StringBuilder();
			appendTo(builder);
			value = builder.toString();
			this.toStringValue = value;
		}
		return value;
	}

	public void appendTo(StringBuilder builder) {
		builder.append(this.type);
		builder.append('/');
		builder.append(this.subtype);
		appendTo(this.parameters, builder);
	}

	private void appendTo(Map<String, String> map, StringBuilder builder) {
		map.forEach((key, val) -> {
			builder.append(';');
			builder.append(key);
			builder.append('=');
			builder.append(val);
		});
	}

	/**
	 * Compares this MIME Type to another alphabetically.
	 *
	 * @param other the MIME Type to compare to
	 * @see MimeTypeUtils#sortBySpecificity(List)
	 */
	@Override
	public int compareTo(MimeType other) {
		int comp = getType().compareToIgnoreCase(other.getType());
		if (comp != 0) {
			return comp;
		}
		comp = getSubtype().compareToIgnoreCase(other.getSubtype());
		if (comp != 0) {
			return comp;
		}
		comp = getParameters().size() - other.getParameters().size();
		if (comp != 0) {
			return comp;
		}

		TreeSet<String> thisAttributes = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
		thisAttributes.addAll(getParameters().keySet());
		TreeSet<String> otherAttributes = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
		otherAttributes.addAll(other.getParameters().keySet());
		Iterator<String> thisAttributesIterator = thisAttributes.iterator();
		Iterator<String> otherAttributesIterator = otherAttributes.iterator();

		while (thisAttributesIterator.hasNext()) {
			String thisAttribute = thisAttributesIterator.next();
			String otherAttribute = otherAttributesIterator.next();
			comp = thisAttribute.compareToIgnoreCase(otherAttribute);
			if (comp != 0) {
				return comp;
			}
			if (PARAM_CHARSET.equals(thisAttribute)) {
				Charset thisCharset = getCharset();
				Charset otherCharset = other.getCharset();
				if (thisCharset != otherCharset) {
					if (thisCharset == null) {
						return -1;
					}
					if (otherCharset == null) {
						return 1;
					}
					comp = thisCharset.compareTo(otherCharset);
					if (comp != 0) {
						return comp;
					}
				}
			} else {
				String thisValue = getParameters().get(thisAttribute);
				String otherValue = other.getParameters().get(otherAttribute);
				if (otherValue == null) {
					otherValue = "";
				}
				comp = thisValue.compareTo(otherValue);
				if (comp != 0) {
					return comp;
				}
			}
		}

		return 0;
	}


	/**
	 * Parse the given String value into a {@code MimeType} object,
	 * with this method name following the 'valueOf' naming convention
	 */
	public static MimeType valueOf(String value) {
		return MimeTypeUtils.parseMimeType(value);
	}


	/**
	 * Comparator to sort {@link MimeType MimeTypes} in order of specificity.
	 *
	 * @param <T> the type of mime types that may be compared by this comparator
	 */
	public static class SpecificityComparator<T extends MimeType> implements Comparator<T> {

		@Override
		public int compare(T mimeType1, T mimeType2) {
			if (mimeType1.isWildcardType() && !mimeType2.isWildcardType()) {  // */* < audio/*
				return 1;
			} else if (mimeType2.isWildcardType() && !mimeType1.isWildcardType()) {  // audio/* > */*
				return -1;
			} else if (!mimeType1.getType().equals(mimeType2.getType())) {  // audio/basic == text/html
				return 0;
			} else {  // mediaType1.getType().equals(mediaType2.getType())
				if (mimeType1.isWildcardSubtype() && !mimeType2.isWildcardSubtype()) {  // audio/* < audio/basic
					return 1;
				} else if (mimeType2.isWildcardSubtype() && !mimeType1.isWildcardSubtype()) {  // audio/basic > audio/*
					return -1;
				} else if (!mimeType1.getSubtype().equals(mimeType2.getSubtype())) {  // audio/basic == audio/wave
					return 0;
				} else {  // mediaType2.getSubtype().equals(mediaType2.getSubtype())
					return compareParameters(mimeType1, mimeType2);
				}
			}
		}

		protected int compareParameters(T mimeType1, T mimeType2) {
			int paramsSize1 = mimeType1.getParameters().size();
			int paramsSize2 = mimeType2.getParameters().size();
			return Integer.compare(paramsSize2, paramsSize1);  // audio/basic;level=1 < audio/basic
		}
	}

}

