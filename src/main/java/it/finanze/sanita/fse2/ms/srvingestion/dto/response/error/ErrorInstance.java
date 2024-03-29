/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 * 
 * Copyright (C) 2023 Ministero della Salute
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
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
