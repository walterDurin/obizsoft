package test;

import org.junit.Test;
import ru.lanit.dibr.utils.gui.PopupBlock;

/**
 * Created with IntelliJ IDEA.
 * User: Vladimir
 * Date: 22.01.14
 * Time: 16:55
 * To change this template use File | Settings | File Templates.
 */
public class TestUI {

    @Test
    public void showBlockPopup() {
        try {
            new PopupBlock("123", "2014-01-22 17:24:23,482 [j2ee14_ws,maxpri=10]] [  STANDARD] [ LoanFWPhase21:01.01] (OVEREDTASKS.Work_Cover_.Action) ERROR  maksim.nenakhov@btc.info - The work object ALFABANK-FW-LOAN-WORK ALFA-2903 could not be locked: Cannot obtain a lock on instance ALFABANK-FW-LOAN-WORK ALFA-2903, as Requestor H6C4B9233EF5F7D6E4C5C9E2915F8DC61 already has the lock");
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
