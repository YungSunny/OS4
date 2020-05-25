package main;

import java.util.Random;

public class PageTable {

    private static Page[] pages = new Page[PMMU.BLOCK_SIZE];

    public static Page findFreePage()
    {
        Random rand = new Random();
        int randomNum;
        do {
            randomNum = rand.nextInt(PMMU.BLOCK_SIZE) % PMMU.BLOCK_SIZE;
            if(pages[randomNum] == null)
                pages[randomNum] = new Page(randomNum);

        } while (pages[randomNum].isAllocated());
        pages[randomNum].allocate();
        return pages[randomNum];
    }

    public void deAllocatePage(int virtualPageIndex){
        if(pages[virtualPageIndex].isAllocated()){
            pages[virtualPageIndex].deAllocate();
        }
    }

    public boolean IsAllocatedPage(int virtualPageIndex) {
        return pages[virtualPageIndex].isAllocated();
    }
}
