package com.jsonde.gui.license.codec.binary;

public class Base64forSomeMethods {
	
	public static byte[] encodeBase64(byte[] binaryData) {
	    return encodeBase64(binaryData, false);
	}

	/**
	 * Encodes binary data using the base64 algorithm into 76 character blocks separated by CRLF.
	 *
	 * @param binaryData binary data to encode
	 * @return String containing Base64 characters.
	 * @since 1.4
	 */
	public static String encodeBase64String(byte[] binaryData) {
	    return StringUtils.newStringUtf8(encodeBase64(binaryData, true));
	}

	/**
	 * Encodes binary data using a URL-safe variation of the base64 algorithm but does not chunk the output. The
	 * url-safe variation emits - and _ instead of + and / characters.
	 *
	 * @param binaryData binary data to encode
	 * @return byte[] containing Base64 characters in their UTF-8 representation.
	 * @since 1.4
	 */
	public static byte[] encodeBase64URLSafe(byte[] binaryData) {
	    return encodeBase64(binaryData, false, true);
	}

	/**
	 * Encodes binary data using a URL-safe variation of the base64 algorithm but does not chunk the output. The
	 * url-safe variation emits - and _ instead of + and / characters.
	 *
	 * @param binaryData binary data to encode
	 * @return String containing Base64 characters
	 * @since 1.4
	 */
	public static String encodeBase64URLSafeString(byte[] binaryData) {
	    return StringUtils.newStringUtf8(encodeBase64(binaryData, false, true));
	}

	/**
	 * Encodes binary data using the base64 algorithm and chunks the encoded output into 76 character blocks
	 *
	 * @param binaryData binary data to encode
	 * @return Base64 characters chunked in 76 character blocks
	 */
	public static byte[] encodeBase64Chunked(byte[] binaryData) {
	    return encodeBase64(binaryData, true);
	}


public static byte[] encodeBase64(byte[] binaryData, boolean isChunked) {
    return encodeBase64(binaryData, isChunked, false);
}

/**
 * Encodes binary data using the base64 algorithm, optionally chunking the output into 76 character blocks.
 *
 * @param binaryData Array containing binary data to encode.
 * @param isChunked  if <code>true</code> this encoder will chunk the base64 output into 76 character blocks
 * @param urlSafe    if <code>true</code> this encoder will emit - and _ instead of the usual + and / characters.
 * @return Base64-encoded data.
 * @throws IllegalArgumentException Thrown when the input array needs an output array bigger than {@link Integer#MAX_VALUE}
 * @since 1.4
 */
public static byte[] encodeBase64(byte[] binaryData, boolean isChunked, boolean urlSafe) {
    return encodeBase64(binaryData, isChunked, urlSafe, Integer.MAX_VALUE);
}

/**
 * Encodes binary data using the base64 algorithm, optionally chunking the output into 76 character blocks.
 *
 * @param binaryData    Array containing binary data to encode.
 * @param isChunked     if <code>true</code> this encoder will chunk the base64 output into 76 character blocks
 * @param urlSafe       if <code>true</code> this encoder will emit - and _ instead of the usual + and / characters.
 * @param maxResultSize The maximum result size to accept.
 * @return Base64-encoded data.
 * @throws IllegalArgumentException Thrown when the input array needs an output array bigger than maxResultSize
 * @since 1.4
 */
public static byte[] encodeBase64(byte[] binaryData, boolean isChunked, boolean urlSafe, int maxResultSize) {
    if (binaryData == null || binaryData.length == 0) {
        return binaryData;
    }

    long len = getEncodeLength(binaryData, CHUNK_SIZE, CHUNK_SEPARATOR);
    if (len > maxResultSize) {
        throw new IllegalArgumentException("Input array too big, the output array would be bigger (" +
                len +
                ") than the specified maxium size of " +
                maxResultSize);
    }

    Base64 b64 = isChunked ? new Base64(urlSafe) : new Base64(0, CHUNK_SEPARATOR, urlSafe);
    return b64.encode(binaryData);
}

/**
 * Pre-calculates the amount of space needed to base64-encode the supplied array.
 *
 * @param pArray         byte[] array which will later be encoded
 * @param chunkSize      line-length of the output (<= 0 means no chunking) between each
 *                       chunkSeparator (e.g. CRLF).
 * @param chunkSeparator the sequence of bytes used to separate chunks of output (e.g. CRLF).
 * @return amount of space needed to encoded the supplied array.  Returns
 *         a long since a max-len array will require Integer.MAX_VALUE + 33%.
 */
public static long getEncodeLength(byte[] pArray, int chunkSize, byte[] chunkSeparator) {
    // base64 always encodes to multiples of 4.
    chunkSize = (chunkSize / 4) * 4;

    long len = (pArray.length * 4) / 3;
    long mod = len % 4;
    if (mod != 0) {
        len += 4 - mod;
    }
    if (chunkSize > 0) {
        boolean lenChunksPerfectly = len % chunkSize == 0;
        len += (len / chunkSize) * chunkSeparator.length;
        if (!lenChunksPerfectly) {
            len += chunkSeparator.length;
        }
    }
    return len;
}

static final int CHUNK_SIZE = 76;
static final byte[] CHUNK_SEPARATOR = { ' ', ' '};

}
