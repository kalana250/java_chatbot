

```markdown
# 🤖 AI Chatbot - Java Swing Application

A modern, feature-rich AI chatbot application built with Java Swing and Groq's free AI API. Features a clean chat interface with real-time AI responses powered by LLaMA 3.3.

![Java](https://img.shields.io/badge/Java-11+-orange.svg)
![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)
![Groq](https://img.shields.io/badge/AI-Groq-purple.svg)
![License](https://img.shields.io/badge/License-MIT-green.svg)

## 📸 Preview

```
┌─────────────────────────────────────────────────────────────┐
│  🤖 AI Chatbot Assistant              [Clear Chat]         │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  🤖 AI Assistant  14:30                                    │
│  ┌─────────────────────────────────────────┐              │
│  │ Hello! I'm your AI assistant.           │              │
│  │ How can I help you today?               │              │
│  └─────────────────────────────────────────┘              │
│                                                             │
│                                    You  14:31              │
│              ┌───────────────────────────────┐             │
│              │ What is Java?                 │             │
│              └───────────────────────────────┘             │
│                                                             │
│  🤖 AI Assistant  14:31                                    │
│  ┌─────────────────────────────────────────┐              │
│  │ Java is a popular programming           │              │
│  │ language used for building...           │              │
│  └─────────────────────────────────────────┘              │
│                                                             │
├─────────────────────────────────────────────────────────────┤
│ Type your message here...                        [Send]    │
└─────────────────────────────────────────────────────────────┘
```

## ✨ Features

- 🎨 **Modern Chat UI** - WhatsApp-style chat bubbles with timestamps
- 🤖 **AI-Powered** - Uses Groq's free LLaMA 3.3 70B model
- 💬 **Context Aware** - Maintains conversation history
- ⚡ **Fast Responses** - Lightning-fast AI replies (1-3 seconds)
- ⌨️ **Keyboard Shortcuts** - Press Enter to send messages
- 🧹 **Clear History** - Reset conversation anytime
- 🎯 **Error Handling** - Graceful error messages and recovery
- 🆓 **100% Free** - No API costs, no credit card required

## 🚀 Quick Start

### Prerequisites

- Java JDK 11 or higher ([Download](https://www.oracle.com/java/technologies/downloads/))
- Maven 3.6+ ([Download](https://maven.apache.org/download.cgi))
- Groq API Key ([Get Free Key](https://console.groq.com/))

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/java-ai-chatbot.git
   cd java-ai-chatbot
   ```

2. **Get your FREE Groq API Key**
    - Visit https://console.groq.com/
    - Sign up (no credit card needed)
    - Go to "API Keys" → "Create API Key"
    - Copy your key (starts with `gsk_`)

3. **Configure API Key**

   Open `src/main/java/OpenAIService.java` and replace:
   ```java
   private static final String API_KEY = "your-groq-api-key-here";
   ```

   With your actual key:
   ```java
   private static final String API_KEY = "gsk_your_actual_key_here";
   ```

4. **Build and Run**
   ```bash
   mvn clean compile exec:java
   ```

That's it! 🎉 The chatbot window should appear.

## 📁 Project Structure

```
java-ai-chatbot/
│
├── pom.xml                          # Maven configuration
├── README.md                        # This file
│
└── src/
    └── main/
        └── java/
            ├── ChatbotUI.java       # Main GUI (Swing interface)
            ├── OpenAIService.java   # API communication handler
            └── Message.java         # Message data model
```

### File Descriptions

| File | Purpose |
|------|---------|
| `ChatbotUI.java` | Main application with Swing GUI, chat bubbles, and event handling |
| `OpenAIService.java` | Handles API requests to Groq, manages conversation history |
| `Message.java` | Data model for chat messages (content, sender, timestamp) |
| `pom.xml` | Maven dependencies and build configuration |

## 🎮 Usage

### Running the Application

**Method 1: Maven Command Line**
```bash
mvn clean compile exec:java
```

**Method 2: IntelliJ IDEA**
1. Open project in IntelliJ
2. Right-click `ChatbotUI.java`
3. Select "Run 'ChatbotUI.main()'"

**Method 3: Eclipse**
1. Import as Maven project
2. Right-click project → Run As → Java Application
3. Select `ChatbotUI`

### Using the Chatbot

1. **Type** your message in the text field at the bottom
2. **Send** by clicking the "Send" button or pressing **Enter**
3. **Wait** for the AI response (usually 1-3 seconds)
4. **Clear** conversation using the "Clear Chat" button
5. **Continue** chatting - the bot remembers context!

## ⚙️ Configuration

### Change AI Model

Edit `OpenAIService.java`:

```java
// Fast and capable (default)
requestBody.put("model", "llama-3.3-70b-versatile");

// Faster, less capable
requestBody.put("model", "llama-3.1-8b-instant");

// Alternative models
requestBody.put("model", "mixtral-8x7b-32768");
requestBody.put("model", "gemma2-9b-it");
```

### Adjust Response Length

```java
requestBody.put("max_tokens", 500);  // 50-2000 recommended
```

### Modify Creativity

```java
requestBody.put("temperature", 0.7);
// 0.0 = focused and deterministic
// 1.0 = balanced (recommended)
// 2.0 = creative and random
```

### Customize Bot Personality

```java
systemMessage.put("content", "You are a helpful and friendly AI assistant.");

// Examples:
// "You are a professional Java programming tutor."
// "You are a funny and casual chatbot."
// "You are an expert in explaining complex topics simply."
```

## 🎨 Customization

### Color Scheme

In `ChatbotUI.java`:

```java
// Header color
headerPanel.setBackground(new Color(70, 130, 180));  // Steel blue

// Send button
sendButton.setBackground(new Color(70, 130, 180));

// User message bubble (HTML)
"background-color: #007bff;"  // Blue

// AI message bubble
"background-color: #f1f1f1;"  // Light gray
```

### Window Size

```java
setSize(800, 600);  // Width x Height
```

### Fonts

```java
// Header
titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

// Input
inputField.setFont(new Font("Arial", Font.PLAIN, 14));

// Messages (in HTML)
"font-family: Arial;"
```

## 🐛 Troubleshooting

### ❌ Error: "Incorrect API key"

**Cause:** Invalid or incorrectly formatted API key

**Solution:**
```java
// ❌ Wrong
private static final String API_KEY = gsk_abc123;      // No quotes
private static final String API_KEY = "ysk_abc123";    // Extra 'y'

// ✅ Correct
private static final String API_KEY = "gsk_abc123...";
```

### ❌ Error: "JSONObject['choices'] not found"

**Cause:** API response doesn't contain expected data

**Solutions:**
1. Check internet connection
2. Verify API key is correct
3. Check Groq status: https://status.groq.com/
4. Review console output for actual error message

### ❌ Maven build fails

```bash
# Clean build
mvn clean install -U

# If persists, clear Maven cache
rm -rf ~/.m2/repository
mvn clean install
```

### ❌ GUI doesn't appear

**On Linux:**
```bash
sudo apt-get install openjdk-11-jdk
export DISPLAY=:0
```

**On macOS:**
```bash
brew install openjdk@11
```

**On Windows:**
- Ensure Java JDK (not just JRE) is installed
- Check `JAVA_HOME` environment variable

### ⚠️ Slow responses

**Solutions:**
1. Switch to faster model: `llama-3.1-8b-instant`
2. Reduce `max_tokens` to 200-300
3. Check internet speed
4. Groq is usually very fast - if slow, check their status

## 📦 Dependencies

```xml
<dependencies>
    <!-- JSON parsing -->
    <dependency>
        <groupId>org.json</groupId>
        <artifactId>json</artifactId>
        <version>20230227</version>
    </dependency>
</dependencies>
```

**Built-in Java libraries used:**
- `java.net.http.*` - HTTP client (Java 11+)
- `javax.swing.*` - GUI framework
- `java.awt.*` - Graphics and layout

## 🔄 Alternative AI APIs

### Switch to OpenAI (Paid)

```java
// In OpenAIService.java
private static final String API_KEY = "sk-proj-your-key";
private static final String API_URL = "https://api.openai.com/v1/chat/completions";

requestBody.put("model", "gpt-3.5-turbo");
```

### Switch to Google Gemini (Free Tier)

1. Get key from https://ai.google.dev/
2. Update endpoint and authentication
3. Modify request format for Gemini API

### Switch to Anthropic Claude

```java
private static final String API_URL = "https://api.anthropic.com/v1/messages";
// Update authentication headers and request format
```

## 📚 API Reference

### Groq API Limits (Free Tier)

| Limit | Value |
|-------|-------|
| Requests/minute | 30 |
| Requests/day | 14,400 |
| Tokens/minute | 14,000 |
| Models | All available |

### Available Groq Models

| Model | Speed | Capability | Context |
|-------|-------|-----------|---------|
| `llama-3.3-70b-versatile` | Fast | High | 128K |
| `llama-3.1-8b-instant` | Very Fast | Medium | 128K |
| `mixtral-8x7b-32768` | Fast | High | 32K |
| `gemma2-9b-it` | Fast | Medium | 8K |

## 🧪 Testing

### Manual Test

1. Run the application
2. Type: "Hello, who are you?"
3. Verify AI responds appropriately
4. Send follow-up: "What can you help me with?"
5. Check conversation context is maintained

### API Connection Test

Create `TestAPI.java`:

```java
public class TestAPI {
    public static void main(String[] args) {
        OpenAIService service = new OpenAIService();
        String response = service.sendMessage("Say 'API connected successfully'");
        System.out.println(response);
    }
}
```

## 🚀 Future Enhancements

- [ ] Save/load conversation history to file
- [ ] Export chat to PDF/TXT
- [ ] Dark mode theme toggle
- [ ] Voice input using speech recognition
- [ ] Text-to-speech for responses
- [ ] Multiple chat tabs
- [ ] Code syntax highlighting in responses
- [ ] Image generation support
- [ ] Streaming responses (word-by-word)
- [ ] Custom themes and skins
- [ ] Multi-language support

## 🤝 Contributing

Contributions are welcome!

1. Fork the repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

## 📄 License

This project is licensed under the MIT License.

```
MIT License

Copyright (c) 2024 [Your Name]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
```

## 👨‍💻 Author

**Your Name**
- GitHub: [@yourusername](https://github.com/yourusername)
- LinkedIn: [Your Name](https://linkedin.com/in/yourprofile)
- Email: your.email@example.com

## 🙏 Acknowledgments

- [Groq](https://groq.com/) - For providing free, fast AI API access
- [OpenAI](https://openai.com/) - For the API specification standard
- [Meta AI](https://ai.meta.com/) - For the LLaMA models
- Java Swing community - For GUI framework documentation

## 📞 Support

Need help?

1. 📖 Check the [Troubleshooting](#-troubleshooting) section
2. 🐛 [Open an issue](https://github.com/yourusername/java-ai-chatbot/issues)
3. 💬 [Start a discussion](https://github.com/yourusername/java-ai-chatbot/discussions)
4. 📧 Email: your.email@example.com

## ⭐ Star History

If this project helped you, please give it a ⭐!

[![Star History Chart](https://api.star-history.com/svg?repos=yourusername/java-ai-chatbot&type=Date)](https://star-history.com/#yourusername/java-ai-chatbot&Date)

## 📊 Stats

![GitHub repo size](https://img.shields.io/github/repo-size/yourusername/java-ai-chatbot)
![GitHub code size](https://img.shields.io/github/languages/code-size/yourusername/java-ai-chatbot)
![Lines of code](https://img.shields.io/tokei/lines/github/yourusername/java-ai-chatbot)

---

**Made with ❤️ and ☕ using Java**

*Happy Coding! 🚀*

---

## 📝 Changelog

### Version 1.0.0 (2024-01-XX)
- ✨ Initial release
- 🤖 Groq API integration
- 🎨 Modern chat UI
- 💬 Conversation history
- 🧹 Clear chat functionality

---

### Quick Command Reference

```bash
# Build project
mvn clean compile

# Run application
mvn exec:java

# Package as JAR
mvn package

# Run JAR
java -jar target/ai-chatbot-1.0-SNAPSHOT.jar

# Clean build directory
mvn clean
```

---

**⚡ TL;DR**

```bash
# 1. Get free API key: https://console.groq.com/
# 2. Add key to OpenAIService.java
# 3. Run:
mvn clean compile exec:java
# 4. Start chatting!
```

🎉 **That's it! Enjoy your AI chatbot!**
```

---

Save this as `README.md` in your project root directory. The file includes:

✅ Complete installation guide  
✅ Usage instructions  
✅ Troubleshooting section  
✅ Customization options  
✅ API documentation  
✅ Contributing guidelines  
✅ Professional formatting with badges  
✅ Code examples  
✅ Quick reference commands  

You can customize the author information, repository links, and other details to match your project!