package jevilmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

public class CarouselRelicAction extends AbstractGameAction {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer p;
    private static final float PADDING = 25.0F * Settings.scale;

    public CarouselRelicAction(){
        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_FAST;
        this.p = AbstractDungeon.player;
    }

    @Override
    public void update() {
        int amount = 1;
        AbstractCard c = null;
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            if (this.p.hand.size() == 0)
            {
                this.isDone = true;
                return;
            }
            else if (this.p.hand.size() == 1)
            {
                c = this.p.hand.getTopCard();
                this.p.hand.moveToExhaustPile(c);
                CardCrawlGame.dungeon.checkForPactAchievement();
            }
            else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, false, false);
                tickDuration();
                return;
            }
        }

        if(c == null) {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    c = card;
                    this.p.hand.moveToExhaustPile(c);
                }
                CardCrawlGame.dungeon.checkForPactAchievement();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }
        }

        if(c != null){
            AbstractCard newCard = AbstractDungeon.returnTrulyRandomCardInCombat();
            UnlockTracker.markCardAsSeen(newCard.cardID);
            if(c.upgraded) newCard.upgrade();
            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(newCard, Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.8F));
            this.isDone = true;
        }

        tickDuration();
    }

}
