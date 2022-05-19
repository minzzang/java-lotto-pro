package lotto.controller;

import lotto.domain.*;
import lotto.view.InputView;
import lotto.view.ResultView;

public class LottoController {

    private final LottoMachine lottoMachine;

    public LottoController() {
        this.lottoMachine = new LottoMachine();
    }

    public Lottos buy() {
        Money purchaseAmount = Money.of(InputView.inputPurchaseAmount());
        LottoCount purchaseCount = LottoPrice.purchase(purchaseAmount);
        LottoCount manualCount = new LottoCount(InputView.inputManualLottoCount());

        LottoCount autoCount = purchaseCount.minus(manualCount);
        Lottos buy = buyLottos(autoCount, manualCount);

        ResultView.printPurchaseCount(autoCount, manualCount);
        ResultView.printLottos(buy);

        return buy;
    }

    private Lottos buyLottos(LottoCount autoCount, LottoCount manualCount) {
        Lottos manualLottos = buyManualLottos(manualCount);
        Lottos autoLottos = buyAutoLottos(autoCount);

        manualLottos.merge(autoLottos);
        return manualLottos;
    }

    private Lottos buyManualLottos(LottoCount manualCount) {
        if (manualCount.isZero()) {
            return new Lottos();
        }
        ManualLottoNumbers manualLottoNumbers = new ManualLottoNumbers(InputView.inputManualLotto(manualCount));
        return lottoMachine.buy(new ManualLottoGenerator(manualLottoNumbers));
    }

    private Lottos buyAutoLottos(LottoCount autoCount) {
        return lottoMachine.buy(new AutoLottoGenerator(autoCount));
    }

    public WinningStatistic checkWinning(Lottos buy) {
        String winningNumbers = InputView.inputWinningNumbers();
        String bonusBall = InputView.inputBonusBall();

        WinningLotto winnings = new WinningLotto(winningNumbers, bonusBall);

        return buy.checkWinnings(winnings);
    }

    public void printResult(WinningStatistic winningStatistic, Money purchaseAmount) {
        ResultView.printWinningStatistic(winningStatistic);
        ResultView.printRateOfReturn(winningStatistic, purchaseAmount);
    }
}
