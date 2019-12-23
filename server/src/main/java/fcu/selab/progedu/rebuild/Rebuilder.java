package fcu.selab.progedu.rebuild;

public class Rebuilder {

    public String extractRevision(String consoleText) {
        String revision;
        String start = "> git checkout -f ";
        String end = "\n";
        revision = consoleText.substring(consoleText.indexOf(start) + start.length(),
                consoleText.indexOf(end));

        return revision.trim();
    }
}
