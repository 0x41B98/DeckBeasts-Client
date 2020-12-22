/*
 * This file is part of PocketBeasts.
 *
 * PocketBeasts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PocketBeasts is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <https://www.gnu.org/licenses/>.
 */
package uk.ac.tees.v8084582.pocketbeasts.networkutil;

/**
 *
 * @author James Fairbairn
 * @author Steven Mead
 */
public interface CardInterface {
    public String getId();
    public String getName();
    public int getManaCost();
    public int getAttack();
    public int getHealth();
    public void recvDamage(int amount);
    public String getElement();
    @Override
    public String toString();
}
