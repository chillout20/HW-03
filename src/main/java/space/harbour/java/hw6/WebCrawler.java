package space.harbour.java.hw6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {
    static ConcurrentLinkedQueue<URL> toVisit = new ConcurrentLinkedQueue<>();
    static CopyOnWriteArraySet<URL> alreadyVisited = new CopyOnWriteArraySet<>();

    public static class UrlVisitor implements Runnable {
        public static String getContentOfWebPage(URL url) {

            final StringBuilder content = new StringBuilder();

            try (InputStream is = url.openConnection().getInputStream();
                 InputStreamReader in = new InputStreamReader(is,"UTF-8");
                 BufferedReader br = new BufferedReader(in);) {
                String inputLine;
                while ((inputLine = br.readLine()) != null)
                    content.append(inputLine);
            }
            catch (IOException e) {
                System.out.println("Failed to retrieve content of " + url.toString());
                e.printStackTrace();
            }

            return content.toString();
        }

        @Override
        public void run() {
            while (!toVisit.isEmpty()) {
                // get a URL from the head of toVisit queue
                URL url = toVisit.poll();
                // mark it as visited by adding to alreadyVisited set
                alreadyVisited.add(url);

                // get content of the web page
                String content = getContentOfWebPage(url);
                System.out.println(Thread.currentThread().getName() + " is visiting " + url);

                String regex = "\\b(https?|http)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]" + "*[-a-zA-Z0-9+&@#/%=~_|]";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(content);

                while (matcher.find()) {
                    synchronized (toVisit) {
                        try {
                            URL newUrl = new URL(matcher.group());
                            if (!alreadyVisited.contains(newUrl) && !toVisit.contains(newUrl)) {
                                toVisit.add(newUrl);
                            }
                        }
                        catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws MalformedURLException {
        final int nThread = 5;
        toVisit.add(new URL("https://chillout20.github.io/chillout20-github-page/"));
        ExecutorService executorService = Executors.newFixedThreadPool(nThread);

        UrlVisitor[] crawlers = new UrlVisitor[nThread];

        for (int i = 0; i < nThread; i++) {
            crawlers[i] = new UrlVisitor();
            executorService.execute(crawlers[i]);
        }
    }

}
