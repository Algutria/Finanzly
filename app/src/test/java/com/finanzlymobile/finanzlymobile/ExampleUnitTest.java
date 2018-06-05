package com.finanzlymobile.finanzlymobile;

import org.junit.Test;

import java.text.NumberFormat;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void boardAlreadyExitsOnCreate(){
        Board board = new Board("My Board Name", "board description", 123456789, new ArrayList<Operation>());
        ArrayList<Board> boards = new ArrayList<>();
        boards.add(board);

        assertTrue(Methods.boardPresent(boards, board.getName()));
    }

    @Test
    public void formattedNumber(){
        assertEquals(Methods.numberToCurrency(120000), "$120,000");
    }
}