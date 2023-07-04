package com.sharetreats.chatbot.module.controller.dto.paymentDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sharetreats.chatbot.module.entity.GiftHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@NoArgsConstructor
public class PayResultButtons {

    public static List<PayResultButtons> of(GiftHistory giftHistory) {
        return List.of(ButtonsForImage.of(6, 1, "none", "https://www.google.com", getProductImage(giftHistory)),
                ButtonsForText.of(6, 1, "none", "상품제목", "<font color=#323232><b>" + getProductName(giftHistory) + "</b></font>", "large", "middle", "left"),
                ButtonsForText.of(6, 1, "none", "브랜드명", "<font color=#323232>" + getBrandNameOfProduct(giftHistory) + "</font>", "small", "top", "left"),
                ButtonsForText.of(6, 4, "none", "발신자명,메시지",
                        "<font color=#323232>From." + getSenderName(giftHistory) + "</font><br>"
                                + "<font color=#323232>Message: " + getMessage(giftHistory) + "</font><br>"
                                + "<font color=#323232><b>Code : " + getGiftCode(giftHistory) + "</b></font><br>"
                                + "<font color=#323232><b>Expire Date : " + getExpirationDate(giftHistory) + "</b></font>", "small", "middle", "left"),
                ButtonsForText.of(6, 3, "none", "수신자명,성공", "<font color=#323232><b>선물을 " + giftHistory.getReceiverName() + "님의 이메일로 성공적으로 전송했습니다.</b></font>", "medium", "middle", "left"),
                ButtonsForText.of(6, 2, "none", "포인트내역", "<font color=#FF0115>사용 포인트 : " + giftHistory.getPrice() + "p</font><br><font color=#323232>남은 포인트 : " + giftHistory.getSender().getPoint() + "p</font>", "medium", "middle", "left"),
                ButtonsForButton.of(6, 1, "open-url", "https://www.google.com", "#6863F2", "<font color=#ffffff>see participating stores</font>", "small", "middle", "middle"),
                ButtonsForButton.of(6, 1, "open-url", "https://www.google.com", "#6863F2", "<font color=#ffffff>terms of use</font>", "small", "middle", "middle"));
    }

    private static String getExpirationDate(GiftHistory giftHistory) {
        return giftHistory.getExpirationDate()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private static String getGiftCode(GiftHistory giftHistory) {
        return giftHistory.getGiftCode();
    }

    private static String getMessage(GiftHistory giftHistory) {
        return giftHistory.getMessage();
    }

    private static String getSenderName(GiftHistory giftHistory) {
        return giftHistory.getSender().getName();
    }

    private static String getBrandNameOfProduct(GiftHistory giftHistory) {
        return giftHistory.getProduct().getBrandName();
    }

    private static String getProductName(GiftHistory giftHistory) {
        return giftHistory.getProduct().getName();
    }

    private static String getProductImage(GiftHistory giftHistory) {
        return giftHistory.getProduct().getImage();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ButtonsForImage extends PayResultButtons {

        @JsonProperty("Columns")
        private int columns;

        @JsonProperty("Rows")
        private int rows;

        @JsonProperty("ActionType")
        private String actionType;

        @JsonProperty("ActionBody")
        private String actionBody;

        @JsonProperty("Image")
        private String image;

        public static ButtonsForImage of(int columns, int rows, String actionType, String actionBody, String image) {
            return new ButtonsForImage(columns, rows, actionType, actionBody, image);
        }
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ButtonsForText extends PayResultButtons {

        @JsonProperty("Columns")
        private int columns;

        @JsonProperty("Rows")
        private int rows;

        @JsonProperty("ActionType")
        private String actionType;

        @JsonProperty("ActionBody")
        private String actionBody;

        @JsonProperty("Text")
        private String text;

        @JsonProperty("TextSize")
        private String textSize;

        @JsonProperty("TextVAlign")
        private String textVAlign;

        @JsonProperty("TextHAlign")
        private String textHAlign;


        public static ButtonsForText of(int columns, int rows, String actionType, String actionBody, String text
                , String textSize, String textVAlign, String textHAlign) {
            return new ButtonsForText(columns, rows, text, actionType, actionBody, textSize, textVAlign, textHAlign);
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ButtonsForButton extends PayResultButtons {
        @JsonProperty("Columns")
        private int columns;

        @JsonProperty("Rows")
        private int rows;

        @JsonProperty("ActionType")
        private String actionType;

        @JsonProperty("ActionBody")
        private String actionBody;

        @JsonProperty("BgColor")
        private String bgColor;

        @JsonProperty("Text")
        private String text;

        @JsonProperty("TextSize")
        private String textSize;

        @JsonProperty("TextVAlign")
        private String textVAlign;

        @JsonProperty("TextHAlign")
        private String textHAlign;

        public static ButtonsForButton of(int columns, int rows, String actionType, String actionBody
                , String bgColor, String text, String textSize, String textVAlign, String textHAlign) {
            return new ButtonsForButton(columns, rows, text, actionType, actionBody, bgColor, textSize, textVAlign, textHAlign);
        }
    }
}
