package editor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class SearchWorker extends SwingWorker<Integer, Object> {
    boolean running = false;

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    protected Integer doInBackground() throws Exception {
        TextEditor textEditor = TextEditor.getInstance();
        setRunning(true);
        textEditor.prepareSearchResults();
        return 1;
    }

    @Override
    protected void done() {
        setRunning(false);
        TextEditor textEditor = TextEditor.getInstance();
        textEditor.searchComplete();
    }
}
