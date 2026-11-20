package com.trivenibricks.service;

import com.trivenibricks.model.Product;
import com.trivenibricks.repository.ProductRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Fills the price list the very first time the app starts.
 * After that the owner edits prices from the admin page and this does nothing.
 */
@Configuration
public class DataSeeder {

    @Bean
    public ApplicationRunner seedProducts(ProductRepository products) {
        return args -> {
            if (products.count() > 0) {
                return;
            }
            products.saveAll(List.of(
                    new Product(
                            "First Class Bricks", "प्रथम श्रेणी ईंट",
                            "Fully baked, sharp edges, deep red colour. Best for walls, pillars and load-bearing work.",
                            "पूरी पकी हुई, सीधे किनारे, गहरा लाल रंग। दीवार, पिलर और भार उठाने वाले काम के लिए सबसे अच्छी।",
                            "9\" x 4\" x 3\"", 7000, "per 1000 bricks", "प्रति 1000 ईंट",
                            "🧱", true, 1),
                    new Product(
                            "Second Class Bricks", "द्वितीय श्रेणी ईंट",
                            "Good strength at a lower rate. Used for boundary walls, filling and plastered work.",
                            "कम दाम में अच्छी मजबूती। चारदीवारी, भराई और पलस्तर वाले काम में इस्तेमाल होती है।",
                            "9\" x 4\" x 3\"", 5500, "per 1000 bricks", "प्रति 1000 ईंट",
                            "🧱", false, 2),
                    new Product(
                            "Fly Ash Bricks", "फ्लाई ऐश ईंट",
                            "Smooth, same size every time, saves plaster and cement. Light grey colour.",
                            "चिकनी, हर ईंट एक ही नाप की, पलस्तर और सीमेंट की बचत। हल्का स्लेटी रंग।",
                            "9\" x 4\" x 3\"", 6500, "per 1000 bricks", "प्रति 1000 ईंट",
                            "⬜", false, 3),
                    new Product(
                            "Picked / Chatka Bricks", "चटका ईंट",
                            "Broken and half bricks sold cheap. Good for filling, levelling and temporary work.",
                            "टूटी और आधी ईंटें सस्ते दाम में। भराई, लेवलिंग और कच्चे काम के लिए अच्छी।",
                            "Mixed sizes", 3000, "per 1000 bricks", "प्रति 1000 ईंट",
                            "🪨", false, 4),
                    new Product(
                            "Brick Ballast (Gitti)", "ईंट की गिट्टी",
                            "Crushed brick used under floors, in roads and for RCC filling work.",
                            "कुटी हुई ईंट, फर्श के नीचे, सड़क और आरसीसी भराई के काम में।",
                            "Crushed", 4500, "per trolley", "प्रति ट्रॉली",
                            "⛏️", false, 5),
                    new Product(
                            "Concrete Hollow Blocks", "कंक्रीट खोखला ब्लॉक",
                            "Light weight, fast to lay, keeps rooms cooler. Good for partition walls.",
                            "वज़न में हल्का, जल्दी चिनाई, कमरा ठंडा रहता है। पार्टीशन दीवार के लिए अच्छा।",
                            "16\" x 8\" x 8\"", 48, "per piece", "प्रति पीस",
                            "🧱", false, 6)
            ));
        };
    }
}
