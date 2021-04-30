/*
 * This file is part of PluginsLib, licensed under the MIT License.
 *
 * Copyright (c) Lorenzo0111
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.lorenzo0111.pluginslib.command;

import org.jetbrains.annotations.Nullable;

/**
 * Class for command customization
 */
@SuppressWarnings("unused")
public final class Customization {
    private final String header;
    private final String notFound;
    private final String noArgs;

    /**
     * @param header Message that is sent every time the sender executes the command
     * @param notFound Message that is sent when the arg does not exists
     * @param noArgs Message that is sent when there are no args ( /command )
     */
    public Customization(@Nullable String header,@Nullable String notFound,@Nullable String noArgs) {
        this.header = header;
        this.notFound = notFound;
        this.noArgs = noArgs;
    }

    /**
     * @return Message that is sent every time the sender executes the command
     */
    @Nullable
    public String getHeader() {
        return header;
    }

    /**
     * @return Message that is sent when the arg does not exists
     */
    @Nullable
    public String getNotFound() {
        return notFound;
    }

    /**
     * @return Message that is sent when there are no args ( /command )
     */
    @Nullable
    public String getNoArgs() {
        return noArgs;
    }
}
