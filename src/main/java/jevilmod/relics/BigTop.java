package jevilmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.MayhemPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BigTop extends CustomRelic {
    public static final String ID = "Big Top";

    public BigTop(){
        super(ID, new Texture("jevil_images/relics/BigTop.png"), new Texture("jevil_images/relics/outline/BigTop.png"), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart(){

        flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MayhemPower(AbstractDungeon.player, 1), 1));
    }

    @Override
    public AbstractRelic makeCopy(){
        return new BigTop();
    }

}
