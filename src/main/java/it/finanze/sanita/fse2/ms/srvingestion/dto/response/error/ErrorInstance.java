/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion.dto.response.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorInstance {

        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static final class Client {
                public static final String UNSUPPORTED = "/unsupported-operation";
        }

        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static final class Server {
                public static final String INTERNAL = "/internal";
                public static final String CONNECTION_REFUSED = "/connection-refused";
        }

        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static final class Resource {
                public static final String EMPTY = "/empty";
                public static final String NOT_FOUND = "/not-found";
                public static final String DOCUMENT_ALREADY_EXISTS = "/document-already-exists";
        }
}
