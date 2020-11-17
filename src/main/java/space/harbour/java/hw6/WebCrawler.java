package space.harbour.java.hw6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {
    static LinkedList<URL> toVisit = new LinkedList<>();
    static Set<URL> alreadyVisited = new HashSet<>();

    public static class UrlVisitor implements Callable<ArrayList<URL>> {
        final URL url;

        public UrlVisitor(URL url) {
            this.url = url;
        }

        public static String getContentOfWebPage(URL url) {
            final StringBuilder content = new StringBuilder();

            try (InputStream is = url.openConnection().getInputStream();
                 InputStreamReader in = new InputStreamReader(is, "UTF-8");
                 BufferedReader br = new BufferedReader(in); ) {
                String inputLine;
                while ((inputLine = br.readLine()) != null)
                    content.append(inputLine);
            } catch (IOException e) {
                System.out.println("Failed to retrieve content of " + url.toString());
                e.printStackTrace();
            }

            return content.toString();
        }

        @Override
        public ArrayList<URL> call() {
            // get content of the web page
            String content = getContentOfWebPage(url);

            String regex = "\\b(https?|http)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]" + "*[-a-zA-Z0-9+&@#/%=~_|]";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(content);

            // get URLs from the content
            ArrayList<URL> urls = new ArrayList<>();

            while (matcher.find()) {
                try {
                    URL newUrl = new URL(matcher.group());
                    urls.add(newUrl);
                    System.out.println(newUrl);
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            for (URL newUrl: urls) {
                if (!alreadyVisited.contains(newUrl) && !toVisit.contains(newUrl)) {
                    toVisit.add(newUrl);
                }
            }

            return urls;
        }
    }

    public static void main(String[] args) throws MalformedURLException, ExecutionException, InterruptedException {
        toVisit.add(new URL("https://chillout20.github.io/chillout20-github-page/"));

        List<Future<ArrayList<URL>>> results = new LinkedList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        boolean allThreadAreDone = false;
        while (!toVisit.isEmpty() || !allThreadAreDone) {
            allThreadAreDone = true;
            // get a URL from the head of toVisit queue
            URL url = toVisit.poll();
            System.out.println(Thread.currentThread().getName() + " is visiting " + url);
            // mark it as visited by adding to alreadyVisited set
            alreadyVisited.add(url);
            results.add(executorService.submit(new UrlVisitor(url)));

            for (Future<ArrayList<URL>> result: results) {
                if (result.isDone()) {
                    results.add(executorService.submit(new UrlVisitor(url)));
                } else allThreadAreDone = false;
            }
        }
    }
}
