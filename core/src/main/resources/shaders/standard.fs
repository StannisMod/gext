#version 330 core

in vec4 vertexColor;
in vec2 texPosition;

out vec4 color;

void main()
{
    color = vertexColor;
}