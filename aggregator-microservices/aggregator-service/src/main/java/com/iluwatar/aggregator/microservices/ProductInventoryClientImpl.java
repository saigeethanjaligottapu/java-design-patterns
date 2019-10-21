/**
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.aggregator.microservices;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * An adapter to communicate with inventory micro-service.
 */
@Component
public class ProductInventoryClientImpl implements ProductInventoryClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductInventoryClientImpl.class);

  @Override
  public int getProductInventories() {
    var response = "0";

    var request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:51516/inventories")).build();
    var client = HttpClient.newHttpClient();
    try {
      var httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
      response = httpResponse.body();
    } catch (IOException ioe) {
      LOGGER.error("IOException Occurred", ioe);
    } catch (InterruptedException ie) {
      LOGGER.error("InterruptedException Occurred", ie);
    }
    return Integer.parseInt(response);
  }
}
