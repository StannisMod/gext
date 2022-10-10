/*
 * Copyright 2022 Stanislav Batalenkov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.stannismod.gext.engine;

import com.github.stannismod.gext.GExt;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public final class ShaderProgram implements AutoCloseable {

    private final int programObject;
    private final int vertexShaderObject;
    private final int fragmentShaderObject;
    private boolean closed;

    public ShaderProgram(String name) {
        programObject = glCreateProgram();

        vertexShaderObject = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShaderObject, readFile(name + ".vs"));
        glCompileShader(vertexShaderObject);
        if (glGetShaderi(vertexShaderObject, GL_COMPILE_STATUS) != 1) {
            GExt.error(glGetShaderInfoLog(vertexShaderObject));
            System.exit(1);
        }

        fragmentShaderObject = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShaderObject, readFile(name + ".fs"));
        glCompileShader(fragmentShaderObject);
        if (glGetShaderi(fragmentShaderObject, GL_COMPILE_STATUS) != 1) {
            GExt.error(glGetShaderInfoLog(fragmentShaderObject));
            System.exit(1);
        }

        glAttachShader(programObject, vertexShaderObject);
        glAttachShader(programObject, fragmentShaderObject);

        glBindAttribLocation(programObject, 0, "vertices");
        glBindAttribLocation(programObject, 1, "textures");

        glLinkProgram(programObject);
        if (glGetProgrami(programObject, GL_LINK_STATUS) != 1) {
            GExt.error(glGetProgramInfoLog(programObject));
            System.exit(1);
        }
        glValidateProgram(programObject);
        if (glGetProgrami(programObject, GL_VALIDATE_STATUS) != 1) {
            GExt.error(glGetProgramInfoLog(programObject));
            System.exit(1);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        if (!closed) {
            close();
        }
        super.finalize();
    }

    @Override
    public void close() {
        closed = true;
        glDetachShader(programObject, vertexShaderObject);
        glDetachShader(programObject, fragmentShaderObject);
        glDeleteShader(vertexShaderObject);
        glDeleteShader(fragmentShaderObject);
        glDeleteProgram(programObject);
    }

    public void setUniform(String uniformName, int value) {
        int location = glGetUniformLocation(programObject, uniformName);
        if (location != -1) glUniform1i(location, value);
    }

    public void setUniform(String uniformName, Vector4f value) {
        int location = glGetUniformLocation(programObject, uniformName);
        if (location != -1) glUniform4f(location, value.x, value.y, value.z, value.w);
    }

    public void setUniform(String uniformName, Matrix4f value) {
        int location = glGetUniformLocation(programObject, uniformName);
        FloatBuffer matrixData = BufferUtils.createFloatBuffer(16);
        value.get(matrixData);
        if (location != -1) glUniformMatrix4fv(location, false, matrixData);
    }

    public void bind() {
        glUseProgram(programObject);
    }

    private String readFile(String filename) {
        StringBuilder outputString = new StringBuilder();
        BufferedReader bufferedReader;
        try {
            URI filePath = getClass().getResource("/shaders/" + filename).toURI();
            bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                outputString.append(line);
                outputString.append("\n");
            }
            bufferedReader.close();
        }
        catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return outputString.toString();
    }
}
