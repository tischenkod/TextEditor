package editor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextEditor extends JFrame {
    private JTextField searchPattern;
    private JTextArea textArea;
    private JFileChooser fileChooser;
    private JCheckBox useRegexCheckBox;
    private JCheckBoxMenuItem useRegexMenuItem;
    private int currentGroup = 0;
    private static TextEditor instance = null;
    private SearchWorker searchWorker;
    private boolean useRegex = false;
    private List<int[]> searchResults = null;

    public static TextEditor getInstance() {
        if (instance == null) {
            instance = new TextEditor();
        }
        return instance;
    }

    public TextEditor() {

        instance = this;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);

        setTitle("Text editor");
        setLayout(new BorderLayout());

        initComponents();

        setVisible(true);

    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(panel, BorderLayout.PAGE_START);
        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        JButton openBtn = new JButton(new ImageIcon("Text Editor\\task\\src\\editor\\fileopen.png"));
        openBtn.setName("OpenButton");
        openBtn.addActionListener(e-> load());
        panel.add(openBtn);

        JButton saveBtn = new JButton(new ImageIcon("Text Editor\\task\\src\\editor\\filesave.png"));
        saveBtn.setName("SaveButton");
        saveBtn.addActionListener(e -> save());
        panel.add(saveBtn);

        searchPattern = new JTextField(25);
        searchPattern.setName("SearchField");
        panel.add(searchPattern);

        JButton startSearchBtn = new JButton(new ImageIcon("Text Editor\\task\\src\\editor\\find.png"));
        startSearchBtn.setName("StartSearchButton");
        startSearchBtn.addActionListener(e -> search());
        startSearchBtn.setSize(20, 20);
        panel.add(startSearchBtn);

        JButton prevSearchBtn = new JButton("<");
        prevSearchBtn.setName("PreviousMatchButton");
        prevSearchBtn.addActionListener(e -> prev());
        prevSearchBtn.setSize(20, 20);
        panel.add(prevSearchBtn);

        JButton nextSearchBtn = new JButton(">");
        nextSearchBtn.setName("NextMatchButton");
        nextSearchBtn.addActionListener(e -> next());
        nextSearchBtn.setSize(20, 20);
        panel.add(nextSearchBtn);

        useRegexCheckBox = new JCheckBox("Use regex");
        useRegexCheckBox.setName("UseRegExCheckbox");
        useRegexCheckBox.addActionListener(this::useRegexSwitch);
        panel.add(useRegexCheckBox);

        textArea = new JTextArea();
        textArea.setName("TextArea");
        textArea.setBounds(0, 0, 295, 280);
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                searchResults = null;
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                searchResults = null;
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                searchResults = null;
            }
        });
        add(textArea, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setName("ScrollPane");
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scroll);

        fileChooser = new JFileChooser();
        fileChooser.setName("FileChooser");

        //Comment out for manual usage!!!
        add(fileChooser);

        // Menu init

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        JMenuItem loadMenuItem = new JMenuItem("Open");
        loadMenuItem.setName("MenuOpen");
        loadMenuItem.addActionListener(e -> load());
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        saveMenuItem.addActionListener(e -> save());
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");
        exitMenuItem.addActionListener(event-> System.exit(0));

        fileMenu.add(loadMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        JMenu searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");
        searchMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(searchMenu);

        JMenuItem startSearchMenuItem = new JMenuItem("Start search");
        startSearchMenuItem.setName("MenuStartSearch");
        startSearchMenuItem.addActionListener(e -> search());

        JMenuItem prevSearchMenuItem = new JMenuItem("Previous search");
        prevSearchMenuItem.setName("MenuPreviousMatch");
        prevSearchMenuItem.addActionListener(e -> prev());

        JMenuItem nextSearchMenuItem = new JMenuItem("Next search");
        nextSearchMenuItem.setName("MenuNextMatch");
        nextSearchMenuItem.addActionListener(e -> next());

        useRegexMenuItem = new JCheckBoxMenuItem("Use regular expressions");
        useRegexMenuItem.setName("MenuUseRegExp");
        useRegexMenuItem.addActionListener(this::useRegexSwitch);

        searchMenu.add(startSearchMenuItem);
        searchMenu.add(prevSearchMenuItem);
        searchMenu.add(nextSearchMenuItem);
        searchMenu.add(useRegexMenuItem);
    }

    private void useRegexSwitch(ActionEvent event) {
        if (event.getSource() instanceof JCheckBoxMenuItem) {
            useRegexCheckBox.setSelected(useRegexMenuItem.isSelected());
        } else {
            useRegexMenuItem.setSelected(useRegexCheckBox.isSelected());
        }
        useRegex = useRegexMenuItem.isSelected();
    }

    private void search() {
        if (useRegex) {
            if (searchWorker != null && searchWorker.isRunning()) {
                searchWorker.cancel(true);
            }
            searchWorker = new SearchWorker();
            searchWorker.execute();
        } else {
            int pos = textArea.getText().indexOf(searchPattern.getText());
            if (pos >= 0) {
                highlightSearchResult(pos, pos + searchPattern.getText().length());
            }
        }
    }

    private void next() {
        if (useRegexCheckBox.isSelected()) {
            if (searchResults == null) {
                return;
            }
            currentGroup = currentGroup < searchResults.size() - 1 ? currentGroup + 1 : 0;
            highlightSearchResult(searchResults.get(currentGroup)[0], searchResults.get(currentGroup)[1]);
        } else {
            int pos = textArea.getText().indexOf(searchPattern.getText(), textArea.getSelectionEnd());
            if (pos >= 0) {
                highlightSearchResult(pos, pos + searchPattern.getText().length());
            } else {
                textArea.grabFocus();
            }
        }
    }

    private void highlightSearchResult(int start, int end) {
        textArea.setCaretPosition(end);
        textArea.select(start, end);
        textArea.grabFocus();
    }

    private void prev() {
        if (useRegexCheckBox.isSelected()) {
            if (searchResults == null) {
                return;
            }
            currentGroup = currentGroup > 0 ? currentGroup - 1: searchResults.size() - 1;
            highlightSearchResult(searchResults.get(currentGroup)[0], searchResults.get(currentGroup)[1]);
        } else {
            int pos = textArea.getText().lastIndexOf(searchPattern.getText(), textArea.getSelectionStart() - 1);
            if (pos == -1) {
                pos = textArea.getText().lastIndexOf(searchPattern.getText());
            }
            if (pos >= 0) {
                textArea.setCaretPosition(pos + searchPattern.getText().length());
                textArea.select(pos, pos + searchPattern.getText().length());
                textArea.grabFocus();
            } else {
                textArea.grabFocus();
            }
        }
    }

    private void load() {
        int chooseResult = fileChooser.showOpenDialog(null);
        if (chooseResult == JFileChooser.APPROVE_OPTION) {
            try {
                textArea.setText((Files.readString(Paths.get(String.valueOf(fileChooser.getSelectedFile())))));
            } catch (IOException ioException) {
                ioException.printStackTrace();
                textArea.setText(null);
            }
        }
    }

    private void save() {
        int chooseResult = fileChooser.showSaveDialog(null);
        if (chooseResult == JFileChooser.APPROVE_OPTION) {
            try (FileOutputStream fos = new FileOutputStream(fileChooser.getSelectedFile(), false)){
                fos.write(textArea.getText().getBytes());
            } catch (IOException ignored) {
            }
        }
    }

    // Called by worker thread
    public void prepareSearchResults() {
        if (javax.swing.SwingUtilities.isEventDispatchThread())
            return;
        Pattern pattern = Pattern.compile(searchPattern.getText());
        Matcher matcher = pattern.matcher(textArea.getText());
        searchResults = new ArrayList<int[]>();
        while (matcher.find()) {
            searchResults.add(new int[]{matcher.start(), matcher.end()});
        }
    }

    public void searchComplete() {
        if (searchResults != null && searchResults.size() > 0) {
            currentGroup = 0;
            highlightSearchResult(searchResults.get(currentGroup)[0], searchResults.get(currentGroup)[1]);
        }
    }
}
