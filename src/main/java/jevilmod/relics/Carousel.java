package jevilmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import jevilmod.actions.CarouselRelicAction;

public class Carousel extends CustomRelic {
    public static final String ID = "Carousel";

    public Carousel(){
        super(ID, new Texture("jevil_images/relics/Carousel.png"), new Texture("jevil_images/relics/outline/Carousel.png"), RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy(){
        return new Carousel();
    }

    @Override
    public void atTurnStartPostDraw(){
        flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new CarouselRelicAction());
    }
}
