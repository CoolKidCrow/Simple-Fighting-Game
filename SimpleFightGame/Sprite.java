import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Sprite extends Pane {
    private AnimationTimer timer;
    private double runTime = 0;
    private double startTime = 0;
    private double vNot = 0;
    private double jumpDir = 1;

    protected ImageView imageView;
    protected int width = 146;
    protected int height = 146;

    private int direction = 1;

    protected int health = 100;
    private double speed = 1.2;
    private double gravity = -9.8;
    private double ground = 160;

    private boolean right = false;
    private boolean left = false;

    private boolean punch = false;
    private boolean kick = false;
    private boolean block = false;
    private boolean crouch = false;
    private boolean jump = false;

    private boolean isPunching = false;
    private boolean isKicking = false;
    private boolean hasJumpKicked = false;
    private boolean isAttacking = false;
    private boolean isBlocking = false;
    private boolean isGrounded = true;
    private boolean isCrouching = false;
    private boolean hasHit = true;
    protected boolean isGameOver = false;

    protected boolean showSauce = false;

    protected Timeline currentAnim;

    protected Timeline idleAnim, runAnim, jumpAnim, crouchAnim, standingPunchAnim, standingKickAnim, crouchingPunchAnim,
            crouchingKickAnim, standingBlockAnim, crouchingBlockAnim, fallingAnim, jumpKickAnim, winAnim, loseAnim;

    protected Box currentCollider, currentAttack;

    protected Box standingCollider, crouchingCollider, standingPunch, standingKick, crouchingPunch, crouchingKick,
            jumpingKick;

    protected ArrayList<Box> colliders = new ArrayList<Box>();

    public Sprite(String source) {
        Image image = new Image(source);
        imageView = new ImageView(image);

        imageView.setScaleX(2);
        imageView.setScaleY(2);

        getChildren().addAll(imageView);

        timer = new AnimationTimer() {
            public void handle(long now) {
                update();
            }
        };
    }

    public void startTimer() {
        timer.start();
        runTime = 0;
    }

    public void stopTimer() {
        timer.stop();
    }

    private void update() {
        if (isGameOver) {
            return;
        }
        runTime += 0.016;
        playerAction();
        playerMovement();
        playerAttack();
        updateCollider();
    }

    public Bounds getCurrentCollider() {
        return currentCollider.getBoundsInParent();
    }

    public Bounds getCurrentAttack() {
        if (currentAttack != null) {
            return currentAttack.getBoundsInParent();
        } else {
            return null;
        }
    }

    public boolean getHasHit() {
        return hasHit;
    }

    public void setHasHit(boolean bool) {
        hasHit = bool;
    }

    public int getDirection() {
        return direction;
    }

    public void changeHealth(Enums.BoxType type) {
        if (type == Enums.BoxType.highHit) {
            if (isBlocking) {
                return;
            }
            health -= 15;
        }
        if (type == Enums.BoxType.lowHit) {
            if (isBlocking && crouch) {
                return;
            }
            health -= 10;
        }
        if (type == Enums.BoxType.overheadHit) {
            if (isBlocking && !crouch) {
                return;
            }
            health -= 20;
        }
        GamePane.checkGameOver();
    }

    public void playLoser() {
        ActionAnimationHandler(loseAnim);
        Kinematics(20, runTime - 0.001, jumpDir);
    }

    public void playWinner() {
        ActionAnimationHandler(winAnim);
    }

    private void updateCollider() {
        for (Box collider : colliders) {
            collider.relocate(getTranslateX() + collider.getOffsetX() + (direction * collider.getDirOffsetX()),
                    getTranslateY() + collider.getOffsetY());
        }
        if (isBlocking || isAttacking || !isGrounded) {
            return;
        }

        currentCollider.setStroke(Color.TRANSPARENT);

        if (crouch) {
            currentCollider = crouchingCollider;
        } else {
            currentCollider = standingCollider;
        }

        if (showSauce) {
            currentCollider.setStroke(Color.GREEN);
        }
    }

    private void playerMovement() {
        if (isAttacking || isBlocking || !isGrounded || crouch) {
            return;
        }
        if (right && !left) {
            if (getTranslateX() < 1100) {
                jumpDir = 1;
                setTranslateX(getTranslateX() + speed);
                MoveAnimationHandler(runAnim);
            } else {
                MoveAnimationHandler(idleAnim);
            }
        }
        if (left && !right) {
            if (getTranslateX() > 0) {
                jumpDir = -1;
                setTranslateX(getTranslateX() - speed);
                MoveAnimationHandler(runAnim);
            } else {
                MoveAnimationHandler(idleAnim);
            }
        }
        if (right && left) {
            jumpDir = 0;
            MoveAnimationHandler(idleAnim);
        }
        if (!right && !left) {
            jumpDir = 0;
            MoveAnimationHandler(idleAnim);
        }
        if (jump && isGrounded) {
            setTranslateY(getTranslateY() - 0.01);
            ActionAnimationHandler(jumpAnim);
            Kinematics(50, runTime - 0.001, jumpDir);
        }

    }

    private void playerAttack() {
        if (block || isAttacking) {
            return;
        }
        if (punch) {
            if (!isPunching) {
                isPunching = true;
                if (crouch && isGrounded) {
                    currentAttack = crouchingPunch;
                    AttackAnimationHandler(crouchingPunchAnim, Enums.AnimType.crouching);
                } else {
                    if (!isGrounded && !hasJumpKicked) {
                        hasJumpKicked = true;
                        currentAttack = jumpingKick;
                        AttackAnimationHandler(jumpKickAnim, Enums.AnimType.jumping);
                    } else if (isGrounded) {
                        currentAttack = standingPunch;
                        AttackAnimationHandler(standingPunchAnim, Enums.AnimType.standing);
                    }
                }
            }
        } else {
            isPunching = false;
        }
        if (kick) {
            if (!isKicking) {
                isKicking = true;
                if (crouch && isGrounded) {
                    currentAttack = crouchingKick;
                    AttackAnimationHandler(crouchingKickAnim, Enums.AnimType.crouching);
                } else {
                    if (!isGrounded && !hasJumpKicked) {
                        hasJumpKicked = true;
                        currentAttack = jumpingKick;
                        AttackAnimationHandler(jumpKickAnim, Enums.AnimType.jumping);
                    } else if (isGrounded) {
                        currentAttack = standingKick;
                        AttackAnimationHandler(standingKickAnim, Enums.AnimType.standing);
                    }
                }
            }
        } else {
            isKicking = false;
        }
    }

    private void playerAction() {
        if (isAttacking) {
            return;
        }
        if (getTranslateY() < ground) {
            Kinematics(vNot, startTime, jumpDir);
            isGrounded = false;
        } else {
            setTranslateY(ground);
            isGrounded = true;
            hasJumpKicked = false;
        }
        if (crouch && isGrounded && !isBlocking) {
            if (!isCrouching) {
                isCrouching = true;
                ActionAnimationHandler(crouchAnim);
            }
        } else {
            isCrouching = false;
        }
        if (block && isGrounded) {
            if (!isBlocking) {
                isBlocking = true;
                if (isCrouching) {
                    ActionAnimationHandler(crouchingBlockAnim);
                } else {
                    ActionAnimationHandler(standingBlockAnim);
                }
            }
        } else {
            isBlocking = false;
        }
    }

    private void MoveAnimationHandler(Timeline animToRun) {
        idleAnim.setRate(direction);
        currentAnim.pause();
        currentAnim = animToRun;
        currentAnim.play();
    }

    private void AttackAnimationHandler(Timeline animToRun, Enums.AnimType type) {
        isAttacking = true;
        currentAnim.pause();
        currentAnim = animToRun;
        currentAnim.playFromStart();
        if (showSauce) {
            currentAttack.setStroke(Color.RED);
        }
        currentAnim.setOnFinished(e -> {
            currentAttack.setStroke(Color.TRANSPARENT);
            currentAttack = null;
            isAttacking = false;
            hasHit = true;
            switch (type) {
                case standing:
                    MoveAnimationHandler(idleAnim);
                    break;
                case crouching:
                    ActionAnimationHandler(crouchAnim);
                    break;
                case jumping:
                    ActionAnimationHandler(fallingAnim);
                    break;
            }
        });
    }

    private void ActionAnimationHandler(Timeline animToRun) {
        currentAnim.pause();
        currentAnim = animToRun;
        currentAnim.playFromStart();
    }

    private void Kinematics(double vNot, double startTime, double dir) {
        this.vNot = vNot;
        this.startTime = startTime;
        jumpDir = dir;
        double deltaTime = (runTime - startTime) * 6;
        setTranslateY(ground - (vNot * deltaTime + (0.5 * gravity * (deltaTime * deltaTime))));
        if (0 < getTranslateX() && getTranslateX() < 1100) {
            setTranslateX(getTranslateX() + dir * speed);
        }
    }

    public void setDirection(int i) {
        if (isAttacking || block || !isGrounded) {
            return;
        }
        imageView.setScaleX(2 * i);
        direction = i;
    }

    public void setAction(Enums.Inputs type, boolean x) {
        switch (type) {
            case punch:
                punch = x;
                break;
            case kick:
                kick = x;
                break;
            case right:
                right = x;
                break;
            case left:
                left = x;
                break;
            case block:
                block = x;
                break;
            case jump:
                jump = x;
                break;
            case crouch:
                crouch = x;
                break;
        }
    }

}
