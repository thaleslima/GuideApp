/*
 * Copyright (C) 2015 Antonio Leiva
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.guideapp.guideapp.model;

public class ViewModel {
    private int id;
    private int idText;
    private String text;
    private int image;
    private int color;

    public int getIdText() {
        return idText;
    }

    public ViewModel(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public ViewModel(int id, int idText, int image, int color) {
        this.id = id;
        this.idText = idText;
        this.image = image;
        this.color = color;
    }

    public void setIdText(int idText) {
        this.idText = idText;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
