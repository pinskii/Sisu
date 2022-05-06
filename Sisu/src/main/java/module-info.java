module Sisu {
    requires javafx.controls;
    requires com.google.gson;
    opens fi.tuni.prog3.sisu to javafx.graphics;
    exports fi.tuni.prog3. sisu;
}