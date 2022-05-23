import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimater {

    private ImageView imageView;
    private int width;
    private int height;

    public SpriteAnimater(ImageView imageView, int width, int height) {
        this.imageView = imageView;
        this.width = width;
        this.height = height;
    }

    public Timeline AnimationMaker(int offsetX, int offsetY, int frames, int cycles, Duration timeBetweenFrames) {
        Timeline anim = new Timeline();
        if (cycles == -1) {
            anim.setCycleCount(Timeline.INDEFINITE);
        } else {
            anim.setCycleCount(cycles);
        }

        for (int i = 0; i < frames; i++) {
            int newFrameOffset = i * (width + 3);
            Duration dur = timeBetweenFrames.add(timeBetweenFrames.multiply(i));

            anim.getKeyFrames().add(new KeyFrame(dur, (ActionEvent e) -> {
                imageView.setViewport(new Rectangle2D(offsetX + newFrameOffset + 1, offsetY + 1, width, height));
            }));
        }
        return anim;
    }

}
