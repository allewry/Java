import java.awt.image.*;
import java.io.*;
import java.nio.file.*;
import java.util.concurrent.*;
import javax.imageio.ImageIO;

public class ImageProcessor {
    private String rootPath;
    private boolean recursive;
    private String action;
    private String extraArg;
    private volatile boolean cancelRequested = false;

    public ImageProcessor(String rootPath, boolean recursive, String action, String extraArg) {
        this.rootPath = rootPath;
        this.recursive = recursive;
        this.action = action;
        this.extraArg = extraArg;
    }

    public void start() {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        Thread escListener = new Thread(() -> {
            try {
                while (System.in.available() == 0) {}
                cancelRequested = true;
                System.out.println("⛔ Операция отменена пользователем.");
            } catch (IOException ignored) {}
        });
        escListener.start();

        try {
            Files.walk(Paths.get(rootPath), recursive ? Integer.MAX_VALUE : 1)
                .filter(Files::isRegularFile)
                .filter(p -> p.toString().endsWith(".jpg") || p.toString().endsWith(".png"))
                .forEach(p -> executor.submit(() -> processImage(p.toFile())));
        } catch (IOException e) {
            System.out.println("❌ Ошибка чтения файлов: " + e.getMessage());
        }

        executor.shutdown();
    }

    private void processImage(File file) {
        if (cancelRequested) return;

        try {
            if (action.equalsIgnoreCase("/r")) {
                boolean deleted = file.delete();
                System.out.println("Удалено: " + file.getName() + " → " + deleted);
            } else if (action.equalsIgnoreCase("/c")) {
                File dest = new File(extraArg, file.getName());
                Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Скопировано: " + file.getName());
            } else if (action.equalsIgnoreCase("/n")) {
                BufferedImage image = ImageIO.read(file);
                BufferedImage negative = createNegative(image);
                ImageIO.write(negative, "png", file);
                System.out.println("Негатив: " + file.getName());
            } else if (action.equalsIgnoreCase("/s")) {
                double scale = Double.parseDouble(extraArg);
                BufferedImage image = ImageIO.read(file);
                int newWidth = (int)(image.getWidth() * scale);
                int newHeight = (int)(image.getHeight() * scale);
                BufferedImage resized = new BufferedImage(newWidth, newHeight, image.getType());
                resized.getGraphics().drawImage(image, 0, 0, newWidth, newHeight, null);
                ImageIO.write(resized, "png", file);
                System.out.println("Масштабировано: " + file.getName());
            }
        } catch (Exception e) {
            System.out.println("⚠️ Ошибка обработки файла " + file.getName() + ": " + e.getMessage());
        }
    }

    private BufferedImage createNegative(BufferedImage original) {
        BufferedImage negative = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
        for (int x = 0; x < original.getWidth(); x++) {
            for (int y = 0; y < original.getHeight(); y++) {
                int rgba = original.getRGB(x, y);
                Color c = new Color(rgba, true);
                Color neg = new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
                negative.setRGB(x, y, neg.getRGB());
            }
        }
        return negative;
    }
}
