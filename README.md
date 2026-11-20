# Triveni Bricks — Website

A bilingual (English + हिंदी) website for a brick kiln business, written in **Java** with
**Spring Boot** and **Thymeleaf**. Built so that a customer with a cheap phone and a slow
connection can find the rate, and call, in two taps.

---

## 1. Start the website

```bash
./run.sh
```

Then open **http://localhost:8080** in a browser.

Press `Control + C` in the terminal to stop it.

> First start takes a minute or two while Maven downloads the libraries. After that it starts in a few seconds.

---

## 2. Put in your real details

Open this one file:

```
src/main/resources/application.yml
```

Every detail the website shows lives under `site:` in that file — phone number, WhatsApp
number, address, owner name, opening hours, GST number.

```yaml
site:
  phone: "+91 8083217096"                 # done
  whatsapp: "+91 8083217096"              # done
  address-en: Sadar Road, Near Panch Mandir
  address-hi: सदर रोड, पंच मंदिर के पास
  city-en: Okani Village                  # add district and state here
  city-hi: ओकनी गाँव                       # add district and state here
  owner-en: Shri XXXXXXXX                 # put the owner's name, or "" to hide the row
  owner-hi: श्री XXXXXXXX
```

Anything still showing `XXXXX` is waiting to be filled in. Fields set to `""` are hidden
from the website completely, so leaving the email or second phone number empty is fine.

Keep the spaces at the start of each line exactly as they are — that is how the file is read.
Save it and restart the website.

**To show the kiln on a map:** open Google Maps, find your kiln, click *Share → Embed a map*,
copy the `src="..."` link from the HTML, and paste just that link into `map-url`.

---

## 3. Pages a customer sees

| Page | Address | What it does |
|---|---|---|
| Home | `/` | Tagline, why-buy-from-us, full rate list, brick calculator |
| Bricks & Rates | `/products` | Every brick type with size and rate |
| About Us | `/about` | Story, who you supply, kiln address and map |
| Get a Quote | `/contact` | Enquiry form + call / WhatsApp / address |

Every page has:
- a **language button** in the header that switches between English and हिंदी
- a **sticky Call + WhatsApp bar** at the bottom on phones
- large text and large buttons, so it works for someone who is not used to websites

---

## 4. The owner's dashboard

Open **http://localhost:8080/admin**.

This project is in a **public** GitHub repository, so the real password is deliberately *not*
stored in any file here. Set it in the terminal before starting the website:

```bash
export TRIVENI_ADMIN_PASSWORD="pick-a-long-private-password"
```

Then run `./run.sh` in that same terminal window. If you skip this step the password is
`change-me-before-going-online`, which is fine while the site only runs on your own computer
but must never be used once the site is on the internet.

From there your father can:
- see **every enquiry** — name, phone, village, brick type, quantity, message
- press **Call** or **WhatsApp** to reach the customer in one tap
- mark an enquiry as *called* so nothing is missed
- **change any brick rate** — it updates on the website straight away
- hide a brick type when it is out of stock
- mark one brick type as *most ordered* so it stands out

Enquiries and rates are saved in the `data/` folder, so they survive a restart.

---

## 5. Changing the brick types

The brick types are created the first time the app runs, in:

```
src/main/java/com/trivenibricks/service/DataSeeder.java
```

Rates can be changed from the dashboard without touching this file. To add or rename a brick
type, edit that file, delete the `data/` folder, and restart.

---

## 6. Changing the words on the site

All text lives in two files, one per language. The keys on the left must stay the same in both:

```
src/main/resources/messages.properties      <-- English
src/main/resources/messages_hi.properties   <-- हिंदी
```

---

## 7. Putting it on the internet

Build one runnable file:

```bash
./mvnw clean package
```

That produces `target/triveni-bricks-1.0.0.jar`. Run it anywhere Java is installed:

```bash
java -jar target/triveni-bricks-1.0.0.jar
```

For a real domain name, any cheap Java host (Railway, Render, a small VPS) can run that jar.
Two things to do before going live:

1. Set `TRIVENI_ADMIN_PASSWORD` to something long and private on the server.
2. Serve the site over `https` so the admin password is not sent in plain text.

---

## 8. Checking nothing is broken

```bash
./mvnw test
```

This opens every page, switches to Hindi, sends a test enquiry, and checks that the dashboard
stays locked without the password. Run it after you change anything.

---

## 9. How the code is organised

```
src/main/java/com/trivenibricks/
├── TriveniBricksApplication.java   starts everything
├── model/
│   ├── Product.java                one row of the rate list
│   └── Enquiry.java                one customer enquiry
├── repository/                     saves and loads from the database
├── controller/
│   ├── PublicController.java       the pages customers see
│   └── AdminController.java        login, dashboard, price changes
├── config/
│   ├── SiteProperties.java         your business details
│   ├── WebConfig.java              language switching, admin guard
│   ├── AdminAuthInterceptor.java   blocks /admin without login
│   └── GlobalModelAdvice.java      puts details on every page
└── service/DataSeeder.java         first-run brick list

src/test/java/com/trivenibricks/
└── WebsiteSmokeTest.java           the checks run by ./mvnw test

src/main/resources/
├── application.yml                 ALL YOUR SETTINGS
├── messages.properties             English text
├── messages_hi.properties          Hindi text
├── templates/                      the HTML pages
└── static/css, static/js           styling and the calculator
```

**Note on the login:** `/admin` is protected by a simple session password, which is enough for
a small business site on a private machine. If this goes on the public internet with real
customer phone numbers in it, serve it over https (point 7 above).
