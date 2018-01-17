package pl.korotkevics.ships.client.localization;

public enum OurLocale {
    ENGLISH {
        @Override
        public String toString() {
            return "en";
        }
    },
    POLISH {
        @Override
        public String toString() {
            return "pl";
        }
    },
    RUSSIAN {
        @Override
        public String toString() {
            return "ru";
        }
    }
}
