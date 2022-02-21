#version 330 core

in vec4 vertexColor;
in vec2 texPosition;

out vec4 color;

uniform sampler2D ourTexture;
uniform int isTextured;

void main()
{
    color = isTextured * texture(ourTexture, texPosition) * vertexColor + (1 - isTextured) * vertexColor;
}