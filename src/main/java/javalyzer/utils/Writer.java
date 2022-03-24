package javalyzer.utils;

public class Writer {

    private static Writer instance;

    public Writer() {
    }

    public static Writer v() {
        if (instance == null) {
            instance = new Writer();
        }
        return instance;
    }

    public void perror(String s) {
        this.print('x', s);
    }

    public void psuccess(String s) {
        this.print('✓', s);
    }

    public void pwarning(String s) {
        this.print('!', s);
    }

    public void pinfo(String s) {
        this.print('*', s);
    }

    private void print(char c, String s) {
        System.out.printf("[%c] %s%n", c, s);
    }
}
