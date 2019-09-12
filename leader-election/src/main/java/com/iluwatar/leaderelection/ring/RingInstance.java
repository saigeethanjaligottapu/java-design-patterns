/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.leaderelection.ring;

import com.iluwatar.leaderelection.Instance;
import com.iluwatar.leaderelection.Message;
import com.iluwatar.leaderelection.MessageManager;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * Implementation with token ring alogirthm. The instances in the system are organized as a ring.
 * Each instance should have a sequential id and the instance with smallest (or largest) id should
 * be the initial leader. All the other instances send heartbeat message to leader periodically
 * to check its health. If one certain instance finds the server done, it will send an election
 * message to the next alive instance in the ring, which contains its own ID. Then the next instance
 * add its ID into the message and pass it to the next. After all the alive instances' ID are add
 * to the message, the message is send back to the first instance and it will choose the instance
 * with smallest ID to be the new leader, and then send a leader message to other instances to
 * inform the result.
 */
public class RingInstance implements Instance, Runnable {

    private MessageManager messageManager;
    private Queue<Message> messageQueue;
    private final int localID;
    private int leaderID;
    private boolean alive;

    /**
     * Constructor of RingInstance.
     */
    public RingInstance(MessageManager messageManager, int localID, int leaderID) {
        this.messageManager = messageManager;
        this.messageQueue = new ConcurrentLinkedQueue<>();
        this.localID = localID;
        this.leaderID = leaderID;
        this.alive = true;
    }

    /**
     * The instance will execute the message in its message queue periodically once it is alive.
     */
    @Override
    public void run() {
        while (true) {
            if (!messageQueue.isEmpty()) {
                this.processMessage(messageQueue.remove());
            }
            System.out.flush();
        }
    }

    /**
     * Message listening method of the instance.
     * @param message
     */
    @Override
    public void onMessage(Message message) {
        messageQueue.offer(message);
    }

    private void processMessage(Message message) {
        switch (message.getType()) {
            case ELECTION:
                System.out.println("Instance " + localID + " - Election Message handling...");
                this.handleElectionMessage(message);
                break;
            case LEADER:
                System.out.println("Instance " + localID + " - Leader Message handling...");
                this.handleLeaderMessage(message);
                break;
            case HEARTBEAT_INVOKE:
                System.out.println("Instance " + localID + " - Heartbeat Message handling...");
                this.handleHeartbeatMessage(message);
                break;
        }
    }

    /**
     * Process the heartbeat invoke message. After receiving the message, the instance will send a heartbeat
     * to leader to check its health. If alive, it will inform the next instance to do the heartbeat. If not,
     * it will start the election process.
     */
    private void handleHeartbeatMessage(Message message) {
        boolean isLeaderAlive = messageManager.sendHeartbeatMessage(this.leaderID);
        if (isLeaderAlive) {
            System.out.println("Instance " + localID + "- Leader is alive.");
            messageManager.sendHeartbeatInvokeMessage(this.localID);
        } else {
            System.out.println("Instance " + localID + "- Leader is not alive. Start election.");
            messageManager.sendElectionMessage(this.localID, String.valueOf(localID));
        }
    }

    /**
     * Process election message. If the local ID is contained in the ID list, the instance will select the
     * alive instance with smallest ID to be the new leader, and send the leader inform message. If not,
     * it will add its local ID to the list and send the message to the next instance in the ring.
     */
    private void handleElectionMessage(Message message) {
        String content = message.getContent();
        System.out.println("Instance " + localID + " - Election Message: " + content);
        List<Integer> candidateList =
                Arrays.stream(content.trim().split(","))
                        .map(Integer::valueOf)
                        .sorted()
                        .collect(Collectors.toList());
        if (candidateList.contains(this.localID)) {
            System.out.println("Instance " + localID + " - New leader should be " + candidateList.get(0) + ". Start leader notification.");
            messageManager.sendLeaderMessage(this.localID, candidateList.get(0));
        } else {
            content += "," + localID;
            messageManager.sendElectionMessage(this.localID, content);
        }
    }

    /**
     * Process leader Message. The instance will set the leader ID to be the new one and send the message to
     * the next instance until all the alive instance in the ring is informed.
     */
    private void handleLeaderMessage(Message message) {
        int newLeaderID = Integer.valueOf(message.getContent());
        if (this.leaderID != newLeaderID) {
            System.out.println("Instance " + localID + " - Update leaderID");
            this.leaderID = newLeaderID;
            messageManager.sendLeaderMessage(this.localID, newLeaderID);
        } else {
            System.out.println("Instance " + localID + " - Leader update done. Start heartbeat.");
            messageManager.sendHeartbeatInvokeMessage(this.localID);
        }
    }

    /**
     * Check whether the certain instnace is alive or not.
     * @return {@code true} if the instance is alive
     */
    @Override
    public boolean isAlive() {
        return alive;
    }

    /**
     * Set the status of instance.
     */
    @Override
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
