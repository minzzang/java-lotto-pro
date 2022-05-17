package lotto.view.message;

public enum InputMessage {

      ENTER_PURCHASE_AMOUNT("구매금액을 입력해 주세요.")
    , ENTER_WINNING_NUMBER("지난 주 당첨 번호를 입력해 주세요.")
    ;

    private final String message;

    InputMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
