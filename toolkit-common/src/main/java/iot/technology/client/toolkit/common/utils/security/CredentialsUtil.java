package iot.technology.client.toolkit.common.utils.security;

import org.eclipse.californium.elements.config.CertificateAuthenticationMode;
import org.eclipse.californium.elements.config.Configuration;
import org.eclipse.californium.elements.util.SslContextUtil;
import org.eclipse.californium.scandium.config.DtlsConfig;
import org.eclipse.californium.scandium.config.DtlsConnectorConfig;
import org.eclipse.californium.scandium.dtls.CertificateType;
import org.eclipse.californium.scandium.dtls.cipher.CipherSuite;
import org.eclipse.californium.scandium.dtls.pskstore.AdvancedMultiPskStore;
import org.eclipse.californium.scandium.dtls.x509.KeyManagerCertificateProvider;
import org.eclipse.californium.scandium.dtls.x509.StaticNewAdvancedCertificateVerifier;

import javax.net.ssl.KeyManager;
import javax.net.ssl.X509KeyManager;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author mushuwei
 */
public class CredentialsUtil {

    // from ETSI Plugtest test spec
    public static final String PSK_IDENTITY = "password";
    public static final byte[] PSK_SECRET = "sesame".getBytes();

    public static final String OPEN_PSK_IDENTITY = "Client_identity";
    public static final byte[] OPEN_PSK_SECRET = "secretPSK".getBytes();

    // CID
    public static final String OPT_CID = "CID:";
    public static final int DEFAULT_CID_LENGTH = 6;

    /**
     * Credentials mode.
     */
    public enum Mode {
        /**
         * Preshared secret keys.
         */
        PSK,
        /**
         * EC DHE, preshared secret keys.
         */
        ECDHE_PSK,
        /**
         * Raw public key certificates.
         */
        RPK,
        /**
         * X.509 certificates.
         */
        X509,
        /**
         * raw public key certificates just trusted (client only).
         */
        RPK_TRUST,
        /**
         * X.509 certificates just trusted (client only).
         */
        X509_TRUST,
        /**
         * Client authentication wanted (server only).
         */
        WANT_AUTH,
        /**
         * No client authentication (server only).
         */
        NO_AUTH,
    }

    /**
     * Default list of modes for clients.
     *
     * Value is PSK, RPK, X509.
     */
    public static final List<Mode> DEFAULT_CLIENT_MODES = Arrays.asList(Mode.PSK);


    /**
     * Setup credentials for DTLS connector.
     *
     * If PSK is provided and no PskStore is already set for the builder, a
     * {@link AdvancedMultiPskStore} containing {@link #PSK_IDENTITY} assigned
     * with {@link #PSK_SECRET}, and {@link #OPEN_PSK_IDENTITY} assigned with
     * {@link #OPEN_PSK_SECRET} set. If PSK is provided with other mode(s) and
     * loading the certificates failed, this is just treated as warning and the
     * configuration is setup to use PSK only.
     *
     * If RPK is provided, the certificates loaded for the provided alias and
     * this certificate is used as identity.
     *
     * If X509 is provided, the trusts are also loaded an set additionally to
     * the credentials for the alias.
     *
     * The Modes can be mixed. If RPK is before X509 in the list, RPK is set as
     * preferred.
     *
     * Examples:
     *
     * <pre>
     * PSK, RPK setup for PSK an RPK.
     * RPK, X509 setup for RPK and X509, prefer RPK
     * PSK, X509, RPK setup for PSK, RPK and X509, prefer X509
     * </pre>
     *
     * @param config DTLS configuration builder. May be already initialized with
     *            PskStore.
     * @param modes list of supported mode. If a RPK is in the list before X509,
     *            or RPK is provided but not X509, then the RPK is setup as
     *            preferred.
     * @throws IllegalArgumentException if loading the certificates fails for
     *             some reason
     */
    public static void setupCredentials(DtlsConnectorConfig.Builder config, List<Mode> modes) {
        boolean plainPsk = modes.contains(Mode.PSK);
        Configuration configuration = config.getIncompleteConfig().getConfiguration();
        StaticNewAdvancedCertificateVerifier.Builder trustBuilder = StaticNewAdvancedCertificateVerifier.builder();
        List<CipherSuite> ciphers = configuration.get(DtlsConfig.DTLS_PRESELECTED_CIPHER_SUITES);
        List<CipherSuite> selectedCiphers = new ArrayList<>();
        for (CipherSuite cipherSuite : ciphers) {
            CipherSuite.KeyExchangeAlgorithm keyExchange = cipherSuite.getKeyExchange();
            if (keyExchange == CipherSuite.KeyExchangeAlgorithm.PSK) {
                if (plainPsk) {
                    selectedCiphers.add(cipherSuite);
                }
            }
        }
        configuration.set(DtlsConfig.DTLS_PRESELECTED_CIPHER_SUITES, selectedCiphers);
    }
}
