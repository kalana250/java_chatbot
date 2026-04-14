import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatbotUI extends JFrame {
    private JTextPane chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private JButton clearButton;
    private OpenAIService aiService;
    private JScrollPane scrollPane;

    public ChatbotUI() {
        aiService = new OpenAIService();
        initializeUI();
    }

    private void initializeUI() {
        // Frame settings
        setTitle("AI Chatbot Assistant");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(240, 240, 245));

        // Header panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Chat area
        chatArea = new JTextPane();
        chatArea.setEditable(false);
        chatArea.setContentType("text/html");
        chatArea.setBackground(Color.WHITE);
        chatArea.setText("<html><body style='padding: 10px; font-family: Arial;'></body></html>");

        scrollPane = new JScrollPane(chatArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = createInputPanel();
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Add welcome message
        addMessage("Hello! I'm your AI assistant. How can I help you today?", false);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("🤖 AI Chatbot Assistant");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        clearButton = new JButton("Clear Chat");
        clearButton.setBackground(new Color(220, 53, 69));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setBorderPainted(false);
        clearButton.setFont(new Font("Arial", Font.BOLD, 12));
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.addActionListener(e -> clearChat());

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(clearButton, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
        inputPanel.setBackground(new Color(240, 240, 245));
        inputPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(10, 10, 10, 10)));
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });

        sendButton = new JButton("Send");
        sendButton.setPreferredSize(new Dimension(100, 40));
        sendButton.setBackground(new Color(70, 130, 180));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendButton.setFocusPainted(false);
        sendButton.setBorderPainted(false);
        sendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sendButton.addActionListener(e -> sendMessage());

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        return inputPanel;
    }

    private void sendMessage() {
        String message = inputField.getText().trim();
        if (message.isEmpty()) {
            return;
        }

        // Add user message to chat
        addMessage(message, true);
        inputField.setText("");

        // Disable input while processing
        setInputEnabled(false);

        // Process AI response in background thread
        SwingWorker<String, Void> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() {
                return aiService.sendMessage(message);
            }

            @Override
            protected void done() {
                try {
                    String response = get();
                    addMessage(response, false);
                } catch (Exception e) {
                    addMessage("Sorry, I encountered an error. Please try again.", false);
                    e.printStackTrace();
                } finally {
                    setInputEnabled(true);
                    inputField.requestFocus();
                }
            }
        };
        worker.execute();
    }

    private void addMessage(String message, boolean isUser) {
        String currentHtml = chatArea.getText();
        String timestamp = new SimpleDateFormat("HH:mm").format(new Date());

        String messageHtml;
        if (isUser) {
            messageHtml = String.format(
                    "<div style='margin: 10px 0; text-align: right;'>" +
                            "<div style='display: inline-block; background-color: #007bff; color: white; " +
                            "padding: 10px 15px; border-radius: 15px 15px 0 15px; max-width: 70%%; text-align: left;'>" +
                            "<strong>You</strong> <span style='font-size: 10px; color: #e0e0e0;'>%s</span><br/>%s" +
                            "</div>" +
                            "</div>",
                    timestamp, message.replace("\n", "<br/>")
            );
        } else {
            messageHtml = String.format(
                    "<div style='margin: 10px 0; text-align: left;'>" +
                            "<div style='display: inline-block; background-color: #f1f1f1; color: black; " +
                            "padding: 10px 15px; border-radius: 15px 15px 15px 0; max-width: 70%%; text-align: left;'>" +
                            "<strong>🤖 AI Assistant</strong> <span style='font-size: 10px; color: #888;'>%s</span><br/>%s" +
                            "</div>" +
                            "</div>",
                    timestamp, message.replace("\n", "<br/>")
            );
        }

        String updatedHtml = currentHtml.replace("</body>", messageHtml + "</body>");
        chatArea.setText(updatedHtml);

        // Auto-scroll to bottom
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }

    private void clearChat() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to clear the chat history?",
                "Clear Chat",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            chatArea.setText("<html><body style='padding: 10px; font-family: Arial;'></body></html>");
            aiService.clearHistory();
            addMessage("Chat cleared. How can I help you?", false);
        }
    }

    private void setInputEnabled(boolean enabled) {
        inputField.setEnabled(enabled);
        sendButton.setEnabled(enabled);
        if (!enabled) {
            sendButton.setText("...");
        } else {
            sendButton.setText("Send");
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            ChatbotUI chatbot = new ChatbotUI();
            chatbot.setVisible(true);
        });
    }
}