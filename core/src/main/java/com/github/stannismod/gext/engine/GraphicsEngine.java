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
        return delegate.begin(mode);
    }

    public static void destroy() {
        delegate.destroy();
    }

    public static int getMajorVersion() {
        return delegate.getMajorVersion();
    }

    public static int getMinorVersion() {
        return delegate.getMinorVersion();
    }
}
