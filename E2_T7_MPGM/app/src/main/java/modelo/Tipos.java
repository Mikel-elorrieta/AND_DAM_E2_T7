package modelo;

    public class Tipos implements java.io.Serializable {

        private static final long serialVersionUID = 3509899683508748863L;
        private int id;
        private String name;
        private String nameEus;

        public Tipos() {
        }

        public Tipos(int id) {
            this.id = id;
        }

        public Tipos(int id, String name, String nameEus) {
            this.id = id;
            this.name = name;
            this.nameEus = nameEus;
        }

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNameEus() {
            return this.nameEus;
        }

        public void setNameEus(String nameEus) {
            this.nameEus = nameEus;
        }

        @Override
        public String toString() {
            return "Tipos{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", nameEus='" + nameEus + '\'' +
                    '}';
        }
    }

