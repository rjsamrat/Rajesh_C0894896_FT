import java.util.ArrayList;
import java.util.List;
class Item {
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
         return name;
    }
}

class SingleObject extends Item {
    public SingleObject(String name) {
        super(name);
    }
}

class Box extends Item {
    private List<Item> contents;
    private int boxNumber;

    public Box(int capacity, int boxNumber) {
        super("Box" + boxNumber);
        this.contents = new ArrayList<>(capacity);
        this.boxNumber = boxNumber;
    }

    public void addItem(Item item) {
        contents.add(item);
    }

    public List<Item> getContents() {
        return contents;
    }

    public int getBoxNumber() {
        return boxNumber;
    }
}


class Move {
    private List<Box> boxes;
    public Move(int capacity) {
        this.boxes = new ArrayList<>(capacity);
    }

    public void addBox(Box box) {
        boxes.add(box);
    }

    public void print() {
        System.out.println("The Objects of my move are:");
        for(Box box : boxes) {
            printContents(box);
        }
    }

    private void printContents(Box box) {
        List<Item> contents = box.getContents();
        for (Item item : contents) {
            if (item instanceof SingleObject) {
                System.out.println(((SingleObject) item).getName());
            }
            else if (item instanceof Box) {
                printContents((Box) item);
            }
        }

    }

    public int find(String itemName) {
        for (Box box : boxes) {
            int foundBoxNumber = findItemInBox(box, itemName);
            if (foundBoxNumber != -1) {
                return foundBoxNumber;
            }
        }
        return  -1;
    }

    private int findItemInBox(Box box, String itemName) {
        List<Item> contents = box.getContents();
        for (Item item : contents) {
            if (item instanceof SingleObject && ((SingleObject) item).getName().equals(itemName)) {
                return box.getBoxNumber();
            }
            else if (item instanceof Box) {
                int foundBoxNumber = findItemInBox((Box) item, itemName);
                if (foundBoxNumber != -1) {
                    return foundBoxNumber;
                }

            }
        }
        return -1;

    }

    public static void main(String[] args) {
        Move move = new Move(2);

        // box 1 contains scissors
        Box box1 = new Box(1, 1);
        box1.addItem(new SingleObject("scissors"));

        // box 2 contains one book
        Box box2 = new Box(1, 2);
        box2.addItem(new SingleObject("book"));

        // box 3 contains one compass
        // and one box containing one scarf
        Box box3 = new Box(2, 3);
        box3.addItem(new SingleObject("compass"));
        Box box4 = new Box(1, 4);
        box4.addItem(new SingleObject("scarf"));
        box3.addItem(box4);

        // We add the three boxes to the first box of move - see below
        Box box5 = new Box(3, 5);
        box5.addItem(box1);
        box5.addItem(box2);
        box5.addItem(box3);

        // We add one box containing 3 objects to move
        Box box6 = new Box(3, 6);
        box6.addItem(new SingleObject("pencils"));
        box6.addItem(new SingleObject("pens"));
        box6.addItem(new SingleObject("rubber"));

        // We add the two most external boxes to the move
        move.addBox(box5);
        move.addBox(box6);

        // We print all the contents of the move
        move.print();

        // We print the number of the outermost cardboard containing the item "scarf"
        System.out.println("The scarf is in the cardboard number " + move.find("scarf"));
    }
}