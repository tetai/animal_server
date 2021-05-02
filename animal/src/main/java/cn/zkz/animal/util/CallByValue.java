package cn.zkz.animal.util;

public class CallByValue {
    public static class User {
        private String name;
        private int age;
        public User(){}
        public User(String name, int age) {
            this.age = age;
            this.name = name;
        }
        public User( User2 user) {
            System.out.printf("222");

        }
        @Override
        public String toString() {
            return this.name + "---"+this.age;
        }
    }
    public static class User2 {
        private String name;
        private int age;
        public User2(){}
        public User2(String name, int age) {
            this.age = age;
            this.name = name;
        }
        public User2( User user) {
            System.out.printf("111");

        }

        @Override
        public String toString() {
            return this.name + "---"+this.age;
        }
    }

    private static User user = null;
    private static User stu = null;

    /**
     * 交换两个对象
     *
     * @param x
     * @param y
     */
    public static void swap(User x, User y) {
        x.name="123";
        y.name="qwe";
    }

}