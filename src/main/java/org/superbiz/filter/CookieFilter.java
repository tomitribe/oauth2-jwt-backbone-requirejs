/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.superbiz.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;


@WebFilter(filterName = "CookieFilter",  urlPatterns = {"/*"})
public class CookieFilter implements Filter {

    private static final Predicate<Cookie> IS_AUTHORIZATION = c -> AccessTokenResponse.AUTHORIZATION.equals(c.getName());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        Optional<Cookie> cookie = Stream.of(request.getCookies())
                .filter(IS_AUTHORIZATION).findFirst();
        CookieConsumer consumer = new CookieConsumer();
        cookie.ifPresent(consumer);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
