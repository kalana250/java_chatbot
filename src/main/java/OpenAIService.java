import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OpenAIService {
    private static final String API_KEY;
    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private List<JSONObject> conversationHistory;

    // Load API key from config.properties file
    static {
        Properties props = new Properties();
        String apiKey = null;

        try {
            // Try to load from file
            InputStream input = new FileInputStream("config.properties");
            props.load(input);
            apiKey = props.getProperty("groq.api.key");
            input.close();

            if (apiKey == null || apiKey.trim().isEmpty()) {
                throw new RuntimeException("API key not found in config.properties");
            }

            System.out.println("✅ API key loaded successfully from config.properties");

        } catch (Exception e) {
            System.err.println("❌ Error loading config.properties: " + e.getMessage());
            System.err.println("📝 Please create config.properties with your API key");
            System.err.println("   Example: groq.api.key=gsk_your_key_here");
            throw new RuntimeException("Failed to load API key from config.properties", e);
        }

        API_KEY = apiKey;
    }

    public OpenAIService() {
        conversationHistory = new ArrayList<>();
        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", "You are a helpful and friendly AI assistant.");
        conversationHistory.add(systemMessage);
    }

    public String sendMessage(String userMessage) {
        try {
            // Add user message to history
            JSONObject userMsg = new JSONObject();
            userMsg.put("role", "user");
            userMsg.put("content", userMessage);
            conversationHistory.add(userMsg);

            // Create request body
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "llama-3.3-70b-versatile");
            requestBody.put("messages", new JSONArray(conversationHistory));
            requestBody.put("max_tokens", 500);
            requestBody.put("temperature", 0.7);

            // Build HTTP request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            // Send request
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            // Debug output
            System.out.println("Status Code: " + response.statusCode());

            // Parse response
            JSONObject jsonResponse = new JSONObject(response.body());

            // Check for errors
            if (jsonResponse.has("error")) {
                JSONObject error = jsonResponse.getJSONObject("error");
                String errorMessage = error.optString("message", "Unknown error");
                conversationHistory.remove(conversationHistory.size() - 1);
                return "❌ API Error: " + errorMessage;
            }

            if (!jsonResponse.has("choices")) {
                conversationHistory.remove(conversationHistory.size() - 1);
                return "❌ Unexpected response format. Please check your API key.";
            }

            // Get AI response
            String aiResponse = jsonResponse.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

            // Add AI response to history
            JSONObject assistantMsg = new JSONObject();
            assistantMsg.put("role", "assistant");
            assistantMsg.put("content", aiResponse);
            conversationHistory.add(assistantMsg);

            return aiResponse;

        } catch (Exception e) {
            e.printStackTrace();
            if (!conversationHistory.isEmpty()) {
                conversationHistory.remove(conversationHistory.size() - 1);
            }
            return "❌ Error: " + e.getMessage();
        }
    }

    public void clearHistory() {
        conversationHistory.clear();
        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", "You are a helpful and friendly AI assistant.");
        conversationHistory.add(systemMessage);
    }
}