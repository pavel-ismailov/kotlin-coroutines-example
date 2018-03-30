package com.tarrk.kotlin_couroutines_example;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.unmodifiableList;

/**
 * That class copied from the Jake Wharton`s Timber.
 * <p/>
 * Logging for lazy people.
 */
public final class L {

    /***/
    public static final int MIN_TAG_LENGTH = 30;

    /***/
    private static final Tree[] TREE_ARRAY_EMPTY = new Tree[0];
    // Both fields guarded by 'FOREST'.
    private static final List<Tree> FOREST = new ArrayList<>();

    /***/
    static volatile Tree[] forestAsArray = TREE_ARRAY_EMPTY;

    public static String string(Object... objects) {

        if (objects == null || objects.length == 0) {
            return "";
        }

        if (objects.length % 2 != 0) {
            return "Number of parameters should be even.";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < objects.length; i = i + 2) {
            /**
             * Pavel: or maybe we can remove last comma other way
             * more light for performance.
             */
            if (i < objects.length - 2) {
                sb
                        .append(objects[i])
                        .append(" - ")
                        .append(objects[i + 1])
                        .append(", ");
            } else {
                sb
                        .append(objects[i])
                        .append(" - ")
                        .append(objects[i + 1]);
            }
        }
        return sb.toString();

    }

    public static void printList(@NonNull String message, List<?> objects) {

        if (objects == null || objects.size() == 0) {
            TREE_OF_SOULS.i(message, "List empty");
            return;
        }

        for (Object o : objects) {
            TREE_OF_SOULS.i(message, o);
        }

    }

    public static <T> void printList(@NonNull String message, T[] objects) {

        if (objects == null || objects.length == 0) {
            TREE_OF_SOULS.i(message, "List empty");
            return;
        }

        for (Object o : objects) {
            TREE_OF_SOULS.i(message, o);
        }

    }

    /**
     * Log a verbose message with optional format args.
     */
    public static void v(@NonNull String message, Object... args) {
        TREE_OF_SOULS.v(message, args);
    }

    /**
     * Log a verbose exception and a message with optional format args.
     */
    public static void v(Throwable t, @NonNull String message, Object... args) {
        TREE_OF_SOULS.v(t, message, args);
    }

    /**
     * Log a debug message with optional format args.
     */
    public static void d(@NonNull String message, Object... args) {
        TREE_OF_SOULS.d(message, args);
    }

    /**
     * Log a debug exception and a message with optional format args.
     */
    public static void d(Throwable t, @NonNull String message, Object... args) {
        TREE_OF_SOULS.d(t, message, args);
    }

    /**
     * Log an info message with optional format args.
     */
    public static void i(@NonNull String message, Object... args) {
        TREE_OF_SOULS.i(message, args);
    }

    /**
     * Log an info exception and a message with optional format args.
     */
    public static void i(Throwable t, @NonNull String message, Object... args) {
        TREE_OF_SOULS.i(t, message, args);
    }

    /**
     * Log a warning message with optional format args.
     */
    public static void w(@NonNull String message, Object... args) {
        TREE_OF_SOULS.w(message, args);
    }

    /**
     * Log a warning exception and a message with optional format args.
     */
    public static void w(Throwable t, @NonNull String message, Object... args) {
        TREE_OF_SOULS.w(t, message, args);
    }

    /**
     * Log an error message with optional format args.
     */
    public static void e(@NonNull String message, Object... args) {
        TREE_OF_SOULS.e(message, args);
    }

    /**
     * Log an error exception and a message with optional format args.
     */
    public static void e(Throwable t, @NonNull String message, Object... args) {
        TREE_OF_SOULS.e(t, message, args);
    }

    /**
     * Log an assert message with optional format args.
     */
    public static void wtf(@NonNull String message, Object... args) {
        TREE_OF_SOULS.wtf(message, args);
    }

    /**
     * Log an assert exception and a message with optional format args.
     */
    public static void wtf(Throwable t, @NonNull String message, Object... args) {
        TREE_OF_SOULS.wtf(t, message, args);
    }

    /**
     * Log at {@code priority} a message with optional format args.
     */
    public static void log(int priority, @NonNull String message, Object... args) {
        TREE_OF_SOULS.log(priority, message, args);
    }

    /**
     * Log at {@code priority} an exception and a message with optional format args.
     */
    public static void log(int priority, Throwable t, @NonNull String message, Object... args) {
        TREE_OF_SOULS.log(priority, t, message, args);
    }

    /**
     * A view into Timber's planted trees as a tree itself. This can be used for injecting a logger
     * instance rather than using static methods or to facilitate testing.
     */
    public static Tree asTree() {
        return TREE_OF_SOULS;
    }

    /**
     * Set a one-time tag for use on the next logging call.
     */
    public static Tree tag(String tag) {
        Tree[] forest = forestAsArray;
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0, count = forest.length; i < count; i++) {
            forest[i].explicitTag.set(tag);
        }
        return TREE_OF_SOULS;
    }

    /**
     * Add a new logging tree.
     */
    public static void plant(Tree tree) {
        if (tree == null) {
            throw new NullPointerException("tree == null");
        }
        if (tree == TREE_OF_SOULS) {
            throw new IllegalArgumentException("Cannot plant L.into itself.");
        }
        synchronized (FOREST) {
            FOREST.add(tree);
            forestAsArray = FOREST.toArray(new Tree[FOREST.size()]);
        }
    }

    /**
     * Remove a planted tree.
     */
    public static void uproot(Tree tree) {
        synchronized (FOREST) {
            if (!FOREST.remove(tree)) {
                throw new IllegalArgumentException("Cannot uproot tree which is not planted: " + tree);
            }
            forestAsArray = FOREST.toArray(new Tree[FOREST.size()]);
        }
    }

    /**
     * Remove all planted trees.
     */
    public static void uprootAll() {
        synchronized (FOREST) {
            FOREST.clear();
            forestAsArray = TREE_ARRAY_EMPTY;
        }
    }

    /**
     * Return a copy of all planted {@linkplain Tree trees}.
     */
    public static List<Tree> forest() {
        synchronized (FOREST) {
            return unmodifiableList(new ArrayList<>(FOREST));
        }
    }

    public static int treeCount() {
        synchronized (FOREST) {
            return FOREST.size();
        }
    }

    /**
     * A {@link Tree} that delegates to all planted trees in the {@linkplain #FOREST forest}.
     */
    private static final Tree TREE_OF_SOULS = new Tree() {

        private final String TAG = L.class.getSimpleName();

        @Override
        public void v(String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].v(message, args);
            }
        }

        @Override
        public void v(Throwable t, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].v(t, message, args);
            }
        }

        @Override
        public void d(String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].d(message, args);
            }
        }

        @Override
        public void d(Throwable t, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].d(t, message, args);
            }
        }

        @Override
        public void i(String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].i(message, args);
            }
        }

        @Override
        public void i(Throwable t, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].i(t, message, args);
            }
        }

        @Override
        public void w(String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].w(message, args);
            }
        }

        @Override
        public void w(Throwable t, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].w(t, message, args);
            }
        }

        @Override
        public void e(String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].e(message, args);
            }
        }

        @Override
        public void e(Throwable t, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].e(t, message, args);
            }
        }

        @Override
        public void wtf(String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].wtf(message, args);
            }
        }

        @Override
        public void wtf(Throwable t, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].wtf(t, message, args);
            }
        }

        @Override
        public void log(int priority, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].log(priority, message, args);
            }
        }

        @Override
        public void log(int priority, Throwable t, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].log(priority, t, message, args);
            }
        }

        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            throw new AssertionError("Missing override for log method.");
        }
    };

    private L() {
        throw new AssertionError("No instances.");
    }

    /**
     * A facade for handling logging calls. Install instances via {@link #plant Timber.plant()}.
     */
    public static abstract class Tree {

        private static final String TAG = Tree.class.getSimpleName();

        final ThreadLocal<String> explicitTag = new ThreadLocal<>();

        String getTag() {
            String tag = explicitTag.get();
            if (tag != null) {
                explicitTag.remove();
            }
            return tag;
        }

        /**
         * Log a verbose message with optional format args.
         */
        public void v(String message, Object... args) {
            prepareLog(Log.VERBOSE, null, message, args);
        }

        /**
         * Log a verbose exception and a message with optional format args.
         */
        public void v(Throwable t, String message, Object... args) {
            prepareLog(Log.VERBOSE, t, message, args);
        }

        /**
         * Log a debug message with optional format args.
         */
        public void d(String message, Object... args) {
            prepareLog(Log.DEBUG, null, message, args);
        }

        /**
         * Log a debug exception and a message with optional format args.
         */
        public void d(Throwable t, String message, Object... args) {
            prepareLog(Log.DEBUG, t, message, args);
        }

        /**
         * Log an info message with optional format args.
         */
        public void i(String message, Object... args) {
            prepareLog(Log.INFO, null, message, args);
        }

        /**
         * Log an info exception and a message with optional format args.
         */
        public void i(Throwable t, String message, Object... args) {
            prepareLog(Log.INFO, t, message, args);
        }

        /**
         * Log a warning message with optional format args.
         */
        public void w(String message, Object... args) {
            prepareLog(Log.WARN, null, message, args);
        }

        /**
         * Log a warning exception and a message with optional format args.
         */
        public void w(Throwable t, String message, Object... args) {
            prepareLog(Log.WARN, t, message, args);
        }

        /**
         * Log an error message with optional format args.
         */
        public void e(String message, Object... args) {
            prepareLog(Log.ERROR, null, message, args);
        }

        /**
         * Log an error exception and a message with optional format args.
         */
        public void e(Throwable t, String message, Object... args) {
            prepareLog(Log.ERROR, t, message, args);
        }

        /**
         * Log an assert message with optional format args.
         */
        public void wtf(String message, Object... args) {
            prepareLog(Log.ASSERT, null, message, args);
        }

        /**
         * Log an assert exception and a message with optional format args.
         */
        public void wtf(Throwable t, String message, Object... args) {
            prepareLog(Log.ASSERT, t, message, args);
        }

        /**
         * Log at {@code priority} a message with optional format args.
         */
        public void log(int priority, String message, Object... args) {
            prepareLog(priority, null, message, args);
        }

        /**
         * Log at {@code priority} an exception and a message with optional format args.
         */
        public void log(int priority, Throwable t, String message, Object... args) {
            prepareLog(priority, t, message, args);
        }

        /**
         * Return whether a message at {@code priority} should be logged.
         */
        protected boolean isLoggable(int priority) {
            return true;
        }

        private void prepareLog(int priority, Throwable t, String message, Object... args) {
            if (!isLoggable(priority)) {
                return;
            }
            if (message != null && message.length() == 0) {
                message = null;
            }
            if (message == null) {
                if (t == null) {
                    return; // Swallow message if it's null and there's no throwable.
                }
                message = getStackTraceString(t);
            } else {
                if (args.length > 0) {
                    if (message.contains("%")) {
                        // that mean user wants to show formatted message
                        message = String.format(message, args);
                    } else {
                        // otherwise simple way
                        message = message + " - ";
                        for (Object o : args) {
                            message = message + o + ", ";
                        }
                        message = message.substring(0, message.length() - 2);
                    }
                }
                if (t != null) {
                    message += "\n" + getStackTraceString(t);
                }
            }

            log(priority, getTag(), message, t);
        }

        private String getStackTraceString(Throwable t) {
            // Don't replace this with Log.getStackTraceString() - it hides
            // UnknownHostException, which is not what we want.
            StringWriter sw = new StringWriter(256);
            PrintWriter pw = new PrintWriter(sw, false);
            t.printStackTrace(pw);
            pw.flush();
            return sw.toString();
        }

        /**
         * Write a log message to its destination. Called for all level-specific methods by default.
         *
         * @param priority Log level. See {@link Log} for constants.
         * @param tag      Explicit or inferred tag. May be {@code null}.
         * @param message  Formatted log message. May be {@code null}, but then {@code t} will not be.
         * @param t        Accompanying exceptions. May be {@code null}, but then {@code message} will not be.
         */
        protected abstract void log(int priority, String tag, String message, Throwable t);
    }

    /**
     * A {@link Tree Tree} for debug builds. Automatically infers the tag from the calling class.
     */
    public static class DebugTree extends Tree {
        private static final int MAX_LOG_LENGTH = 4000;
        private static final int CALL_STACK_INDEX = 5;
        private static final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$");

        /**
         * Extract the tag which should be used for the message from the {@code element}. By default
         * this will use the class name without any anonymous class suffixes (e.g., {@code Foo$1}
         * becomes {@code Foo}).
         * <p/>
         * Note: This will not be called if a {@linkplain #tag(String) manual tag} was specified.
         */
        protected String createStackElementTag(StackTraceElement element) {
            String tag = element.getClassName();
            Matcher m = ANONYMOUS_CLASS.matcher(tag);
            if (m.find()) {
                tag = m.replaceAll("");
            }
            return tag.substring(tag.lastIndexOf('.') + 1);
        }

        @Override
        final String getTag() {
            String tag = super.getTag();
            if (tag != null) {
                return tag;
            }

            // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
            // because Robolectric runs them on the JVM but on Android the elements are different.
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            if (stackTrace.length <= CALL_STACK_INDEX) {
                throw new IllegalStateException(
                        "Synthetic stacktrace didn't have enough elements: are you using proguard?");
            }
            return createStackElementTag(stackTrace[CALL_STACK_INDEX]);
        }

        /**
         * Break up {@code message} into maximum-length chunks (if needed) and send to either
         * {@link Log#println(int, String, String) Log.println()} or
         * {@link Log#wtf(String, String) Log.wtf()} for logging.
         * <p/>
         * {@inheritDoc}
         */
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {

            if (message.length() < MAX_LOG_LENGTH) {
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, message);
                } else {
                    String s = padStart(tag, MIN_TAG_LENGTH);
                    Log.println(priority, padStart(tag, MIN_TAG_LENGTH), message);
                }
                return;
            }

            // Split by line, then ensure each line can fit into Log's maximum length.
            for (int i = 0, length = message.length(); i < length; i++) {
                int newline = message.indexOf('\n', i);
                newline = newline != -1 ? newline : length;
                do {
                    int end = Math.min(newline, i + MAX_LOG_LENGTH);
                    String part = message.substring(i, end);
                    if (priority == Log.ASSERT) {
                        Log.wtf(tag, part);
                    } else {
                        Log.println(priority, padStart(tag, MIN_TAG_LENGTH), part);
                    }
                    i = end;
                } while (i < newline);
            }
        }
    }

    private static String padStart(String s, int length) {
        /**
         * Pavel: changed  to "-->" and move it to tag to better looking in IDEA output
         */
        StringBuilder sb = new StringBuilder();
        sb.append("-->");
        for (int i = 0; i < length - s.length(); i++) {
            sb.append("\u0020");
        }
        sb.append(s);
        return new String(sb);
    }

}