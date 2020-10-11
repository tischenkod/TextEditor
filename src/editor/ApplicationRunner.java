package editor;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApplicationRunner {
    public static void main(String[] args) {
        String s  = "          Sonnet I\n" +
                "\n" +
                "\n" +
                "FROM fairest creatures we desire increase,\n" +
                "That thereby beauty's rose might never die,\n" +
                "But as the riper should by time decease,\n" +
                "His tender heir might bear his memory:\n" +
                "But thou, contracted to thine own bright eyes,\n" +
                "Feed'st thy light'st flame with self-substantial fuel,\n" +
                "Making a famine where abundance lies,\n" +
                "Thyself thy foe, to thy sweet self too cruel.\n" +
                "Thou that art now the world's fresh ornament\n" +
                "And only herald to the gaudy spring,\n" +
                "Within thine own bud buriest thy content\n" +
                "And, tender churl, makest waste in niggarding.\n" +
                "Pity the world, or else this glutton be,\n" +
                "To eat the world's due, by the grave and thee.\n" +
                "\n" +
                " Sonnet II\n" +
                "       \n" +
                "         \n" +
                "When forty winters shall beseige thy brow,\n" +
                "And dig deep trenches in thy beauty's field,\n" +
                "Thy youth's proud livery, so gazed on now,\n" +
                "Will be a tatter'd weed, of small worth held:\n" +
                "Then being ask'd where all thy beauty lies,\n" +
                "Where all the treasure of thy lusty days,\n" +
                "To say, within thine own deep-sunken eyes,\n" +
                "Were an all-eating shame and thriftless praise.\n" +
                "How much more praise deserved thy beauty's use,\n" +
                "If thou couldst answer 'This fair child of mine\n" +
                "Shall sum my count and make my old excuse,'\n" +
                "Proving his beauty by succession thine!\n" +
                "This were to be new made when thou art old,\n" +
                "And see thy blood warm when thou feel'st it cold.\n" +
                "\n" +
                "Sonnet III\n" +
                "\n" +
                "\n" +
                "Look in thy glass, and tell the face thou viewest\n" +
                "Now is the time that face should form another;\n" +
                "Whose fresh repair if now thou not renewest,\n" +
                "Thou dost beguile the world, unbless some mother.\n" +
                "For where is she so fair whose unear'd womb\n" +
                "Disdains the tillage of thy husbandry?\n" +
                "Or who is he so fond will be the tomb\n" +
                "Of his self-love, to stop posterity?\n" +
                "Thou art thy mother's glass, and she in thee\n" +
                "Calls back the lovely April of her prime:\n" +
                "So thou through windows of thine age shall see\n" +
                "Despite of wrinkles this thy golden time.\n" +
                "But if thou live, remember'd not to be,\n" +
                "Die single, and thine image dies with thee.\n" +
                "\n" +
                "Sonnet IV\n" +
                "\n" +
                "\n" +
                "Unthrifty loveliness, why dost thou spend\n" +
                "Upon thyself thy beauty's legacy?\n" +
                "Nature's bequest gives nothing but doth lend,\n" +
                "And being frank she lends to those are free.\n" +
                "Then, beauteous niggard, why dost thou abuse\n" +
                "The bounteous largess given thee to give?\n" +
                "Profitless usurer, why dost thou use\n" +
                "So great a sum of sums, yet canst not live?\n" +
                "For having traffic with thyself alone,\n" +
                "Thou of thyself thy sweet self dost deceive.\n" +
                "Then how, when nature calls thee to be gone,\n" +
                "What acceptable audit canst thou leave?\n" +
                "Thy unused beauty must be tomb'd with thee,\n" +
                "Which, used, lives th' executor to be.";
//        System.out.println(s);
        TextEditor.getInstance();
//        new TextEditor();
    }
}
