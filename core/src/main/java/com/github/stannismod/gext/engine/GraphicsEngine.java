package com.github.stannismod.gext.engine;

public class GraphicsEngine {

    private static IGraphicsEngine<? extends IBufferBuilder<?>> delegate;

    public static <T extends IBufferBuilder<T>> void setDelegate(IGraphicsEngine<T> delegate) {
        GraphicsEngine.delegate = delegate;
    }

    public static void init() {
        delegate.init();
    }

//    public static void setGlDeprecated(boolean deprecated) {
//        if (deprecated) {
//            GlStateManager.setDelegate(new DeprecatedGlStateManager());
//        } else {
//            GlStateManager.setDelegate(new ModernGlStateManager(delegate));
//        }
//    }

    public static IBufferBuilder<?> begin(int mode) {
        return begin(mode, null);
    }

    public static IBufferBuilder<?> begin(int mode, VertexFormat format) {
        return delegate.begin(mode, format);
    }

    public static void destroy() {
        delegate.destroy();
    }

    public static void run(Runnable r) {
        delegate.run(r);
    }

    public static int getMajorVersion() {
        return delegate.getMajorVersion();
    }

    public static int getMinorVersion() {
        return delegate.getMinorVersion();
    }
}
