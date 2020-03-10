package chapter2.part5;

import chapter2.part4.MaxPQ;
import chapter2.part4.MinPQ;

/**
 * Ex2.5.22
 */
public class StockMarket {
    private class Order implements Comparable<Order> {
        int shares;
        double price;

        public Order(int shares, double price) {
            this.shares = shares;
            this.price = price;
        }

        public int compareTo(Order that) {
            if (this.price < that.price) {
                return -1;
            } else if (this.price > that.price) {
                return 1;
            } else {
                return this.shares - that.shares;
            }
        }

        public String toString() {
            return "price: " + price + ", shares: " + shares;
        }
    }

    /**
     * we have a match if BuyOrder's max buy price >= SellOrder's min sell price, or the price the buyer is willing to
     * pay is >= the price the seller is willing to sell. We make a transaction by subtracting the number of shares the buyer
     * wants with the number of shares the seller has. If BuyOrder's shares > SellOrder's shares, BuyerOrder stays in the market
     * If SellOrder's shares > BuyOrder' shares, SellOder stays in the martket with the remaining shares.
     *
     * If BuyOrder's shares = SellOrder's shares, both are removed from the market.
     *
     * The priority queue for buyer should be a maximum priority queue (on price), so we could put the most willing buyer on top
     * The priority queue for seller should be a minimum priority queue (on price), so we could put the most willing seller on top.
     * For both of the queues, if we have a tie in price, the buyer/seller willing to buy/sell more shares should have higher priority.
     */

    private MaxPQ<Order> buyerQueue;
    private MinPQ<Order> sellerQueue;

    public StockMarket() {
        buyerQueue = new MaxPQ<>();
        sellerQueue = new MinPQ<>();
    }

    public void simulate(double maxPrice, int maxShares) {
        boolean isBuy = Math.random() < 0.5 ? true : false;
        double price = Math.random() * maxPrice;
        int shares = (int) (Math.random() * maxShares);
        Order order = new Order(shares, price);
        if (isBuy) {
            buyerQueue.insert(order);
        } else {
            sellerQueue.insert(order);
        }
        if (!buyerQueue.isEmpty() && !sellerQueue.isEmpty()) {
            Order buyOrder = buyerQueue.max();
            Order sellOrder = sellerQueue.min();
            if (match(buyOrder, sellOrder)) {
                System.out.println("---------Found a match--------------");
                System.out.println("Buy order is " + buyOrder);
                System.out.println("Sell order is " + sellOrder);
                buyerQueue.delMax();
                sellerQueue.delMin();
                trade(buyOrder, sellOrder);
            }
        }
    }

    private boolean match(Order buyOrder, Order sellOrder) {
        return buyOrder.price >= sellOrder.price;
    }

    private void trade(Order buyOrder, Order sellOrder) {
        if (buyOrder.shares > sellOrder.shares) {
            buyOrder.shares -= sellOrder.shares;
            System.out.println("After trade, buy order is " + buyOrder);
            System.out.println("Reinserting buy order into the queue...");
            buyerQueue.insert(buyOrder);
        } else if (buyOrder.shares < sellOrder.shares) {
            sellOrder.shares -= buyOrder.shares;
            System.out.println("After trade, sell order is " + sellOrder);
            System.out.println("Reinserting sell order into the queue...");
            sellerQueue.insert(sellOrder);
        } else {
            System.out.println("Trade completed. Both orders are removed");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        double maxPrice = Double.parseDouble(args[0]);
        int maxShares = Integer.parseInt(args[1]);
        StockMarket market = new StockMarket();
        while(true) {
            market.simulate(maxPrice, maxShares);
            Thread.sleep(5000);
        }
    }
}
