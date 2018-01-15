package pl.korotkevics.ships.client.localization;

public enum Locale {
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
    };
}
