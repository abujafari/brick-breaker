package sample;


public class Engine {

    private OnAction onAction;
    private int fps = 15;
    private Thread updateThread;

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    /**
     * @param fps set fps and we convert it to millisecond
     */
    public void setFps(int fps) {
        this.fps = fps;
    }

    private synchronized void Update() {
        updateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        onAction.onUpdate();
                        Thread.sleep(fps);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        updateThread.start();
    }

    private void Initialize() {
        onAction.onInit();
    }

    private  synchronized void PhysicsCalculation() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        onAction.onPhysicsUpdate();
                        Thread.sleep(fps);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void start() {
        Initialize();
        Update();
        PhysicsCalculation();
    }


    public interface OnAction {
        void onUpdate();

        void onInit();

        void onPhysicsUpdate();
    }

}
