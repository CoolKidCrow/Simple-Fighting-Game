import javafx.util.Duration;

public class Ken extends Sprite {

    private SpriteAnimater ken;

    public Ken(Boolean bool) {
        super("/Assets/SpriteSheets/KenSimpleFightingGameSheet.png");
        showSauce = bool;
        ken = new SpriteAnimater(imageView, width, height);

        idleAnim = ken.AnimationMaker(1, 1, 4, -1, Duration.millis(90));
        runAnim = ken.AnimationMaker(1, 150, 5, -1, Duration.millis(90));
        jumpAnim = ken.AnimationMaker(1, 597, 5, 1, Duration.millis(90));
        standingPunchAnim = ken.AnimationMaker(1, 299, 5, 1, Duration.millis(70));
        standingKickAnim = ken.AnimationMaker(1, 448, 5, 1, Duration.millis(70));
        crouchAnim = ken.AnimationMaker(1, 746, 1, 1, Duration.millis(90));
        crouchingPunchAnim = ken.AnimationMaker(1, 895, 4, 1, Duration.millis(90));
        crouchingKickAnim = ken.AnimationMaker(1, 1044, 3, 1, Duration.millis(90));
        standingBlockAnim = ken.AnimationMaker(1, 1193, 1, 1, Duration.millis(90));
        crouchingBlockAnim = ken.AnimationMaker(150, 1193, 1, 1, Duration.millis(90));
        fallingAnim = ken.AnimationMaker(597, 597, 1, 1, Duration.millis(90));
        jumpKickAnim = ken.AnimationMaker(1, 1342, 2, 1, Duration.millis(90));
        winAnim = ken.AnimationMaker(1, 1491, 3, 1, Duration.millis(90));
        loseAnim = ken.AnimationMaker(1, 1640, 3, 1, Duration.millis(90));

        standingCollider = new Box(0, 45, 60, 55, 160, Enums.BoxType.hurtBox);
        crouchingCollider = new Box(10, 45, 115, 55, 110, Enums.BoxType.hurtBox);
        standingPunch = new Box(50, 33, 75, 80, 20, Enums.BoxType.highHit);
        standingKick = new Box(55, 38, 45, 70, 60, Enums.BoxType.highHit);
        crouchingPunch = new Box(50, 42, 125, 61, 20, Enums.BoxType.lowHit);
        crouchingKick = new Box(50, -3, 180, 150, 30, Enums.BoxType.lowHit);
        jumpingKick = new Box(40, 54, 65, 36, 60, Enums.BoxType.overheadHit);

        colliders.add(standingCollider);
        colliders.add(crouchingCollider);
        colliders.add(standingPunch);
        colliders.add(standingKick);
        colliders.add(crouchingPunch);
        colliders.add(crouchingKick);
        colliders.add(jumpingKick);
        currentCollider = standingCollider;

        currentAnim = idleAnim;
        currentAnim.play();
    }
}
