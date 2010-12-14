/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010  Jochem Van denbussche
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * HttpServerController.java
 * Created on Dec 8, 2010, 9:10:12 PM
 */
package cli;

import domain.http.HttpConstants;
import domain.http.HttpServer;
import domain.shutdown.ShutdownHook;
import domain.upnp.HttpRequestHandler;
import domain.upnp.HttpResource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpServerController {

    private HttpServer httpServer;
    private HttpServerCli httpServerCli;
    private HttpRequestHandler httpRequestHandler;

    public HttpServerController(ShutdownHook shutdownHook, HttpServer server) {
        this.httpServer = server;
        this.httpRequestHandler = new HttpRequestHandler();
        test();
        httpServer.addRequestHandler(httpRequestHandler);
        this.httpServerCli = new HttpServerCli(this);
        shutdownHook.addShutdownListener(httpServerCli);
        httpServer.addRequestHandler(httpServerCli);
    }

    /**
     * Starts the HTTP server.
     * @throws IOException if an I/O error occurs while starting the HTTP server
     */
    public void start() throws IOException {
        httpServer.start();
    }

    /**
     * Stops the HTTP server
     */
    public void stop() {
        httpServer.stop();
    }

    public int getPort() {
        return httpServer.getPort();
    }

    public String getBindAddr() {
        return httpServer.getBindAddr().getHostAddress();
    }

    /**
     * Add a HttpResource object.<br />
     * see {@link HttpResource}
     * @param relativeUri the URI this resource should be located at
     * @param resource the resource to add
     */
    public void addHttpResource(URI relativeUri, HttpResource resource) {
        httpRequestHandler.addHttpResource(relativeUri, resource);
        System.out.println("Adding HTTP resource: " + relativeUri);
    }

    private void test() {
        String strDir = Settings.getProperty(Settings.HTTP_DIR);
        if (strDir == null) {
            simpleTest();
            return;
        }
        File dir = new File(strDir);
        if (!dir.isDirectory()) {
            simpleTest();
            return;
        }
        List<File> files = listFiles(dir);
        for (File f : files) {
            addResource(dir, f);
        }
        addRoot();
    }

    private void simpleTest() {
        String content = "<META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n"
                + "<html><body>Java HttpServer works!</body></html>";
        HttpResource r1;
        try {
            r1 = new HttpResource(content.getBytes("UTF-8"), "text/html", "utf-8");
            addHttpResource(new URI("/"), r1);
        } catch (UnsupportedEncodingException ex) {
            // should not happen
        } catch (URISyntaxException ex) {
            // should not happen
        }
    }

    private List<File> listFiles(File dir) {
        List<File> result = new ArrayList<File>();
        File[] sub = dir.listFiles();
        for (int i = 0; i < sub.length; i++) {
            if (sub[i].isDirectory()) {
                result.addAll(listFiles(sub[i]));
            } else if (isValidFile(sub[i])) {
                result.add(sub[i]);
            }
        }
        return result;
    }

    private boolean isValidFile(File file) {
        return (file.getName().endsWith(".html") ||
                file.getName().endsWith(".png") ||
                file.getName().endsWith(".jpg") ||
                file.getName().endsWith(".jpeg") ||
                file.getName().endsWith(".gif"));
    }

    private void addResource(File root, File f) {
        if (f.getName().endsWith(".html")) {
            addHtml(root, f);
        } else if (f.getName().endsWith(".png")) {
            addImage("png", root, f);
        } else if (f.getName().endsWith(".jpg") || f.getName().endsWith(".jpeg")) {
            addImage("jpeg", root, f);
        } else if (f.getName().endsWith(".gif")) {
            addImage("gif", root, f);
        }
    }

    private void addHtml(File root, File f) {
        String total = "";
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(f);
            br = new BufferedReader(fr);
            String s;
            while((s = br.readLine()) != null) {
                total += s + HttpConstants.CRLF;
            }
        } catch (Exception e) {
            return;
        } finally {
            if (fr != null) {
                try { fr.close(); } catch (IOException ex) {}
            }
            if (br != null) {
                try { br.close(); } catch (IOException ex) {}
            }
        }
        HttpResource r1;
        try {
            r1 = new HttpResource(total.getBytes("UTF-8"), "text/html", "utf-8");
            URI uri = null;
            try {
                uri = new URI( "/" + root.toURI().relativize(f.toURI()).toString());
            } catch (URISyntaxException ex) {
                // Should not happen
            }
            addHttpResource(uri, r1);
        } catch (UnsupportedEncodingException ex) {
            // should not happen
        }
    }

    private void addImage(String type, File root, File f) {
        InputStream in = null;
        long size = f.length();
        if (size > 1048576) { // Support up to 1 megabyte
            return;
        }
        byte[] buf = new byte[(int)size];
        int offset = 0;
        int numRead = 0;
        try {
            in = new FileInputStream(f);
            while (offset < buf.length
                    && (numRead = in.read(buf, offset, buf.length - offset)) >= 0) {
                offset += numRead;
            }
        } catch (Exception ex) {
            return;
        } finally {
            if (in != null) {
                try { in.close(); } catch (IOException ex) {}
            }
        }
        HttpResource r1;
        r1 = new HttpResource(buf, "image/" + type);
        URI uri = null;
        try {
            uri = new URI( "/" + root.toURI().relativize(f.toURI()).toString());
        } catch (URISyntaxException ex) {
            // Should not happen
        }
        addHttpResource(uri, r1);
    }

    private void addRoot() {
        URI root = null;
        try {
            root = new URI("/");
        } catch (URISyntaxException ex) {
            // Should not happen
        }
        String content = "<META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n"
                + "<html><body><h1>Index of /</h1><ul>";
        Iterator<Entry<URI, HttpResource>> it = httpRequestHandler.getHttpResources();
        while (it.hasNext()) {
            Entry<URI, HttpResource> entry = it.next();
            content = content.concat(
                    "<li><a href=\"" + entry.getKey() + "\">" + root.relativize(entry.getKey()) + "</a></li>");
        }
        content = content.concat("</ul></body></html>");
        HttpResource r1;
        try {
            r1 = new HttpResource(content.getBytes("UTF-8"), "text/html", "utf-8");
            addHttpResource(new URI("/"), r1);
        } catch (UnsupportedEncodingException ex) {
            // should not happen
        } catch (URISyntaxException ex) {
            // should not happen
        }
    }
}
