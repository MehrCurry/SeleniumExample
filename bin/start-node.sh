#!/bin/sh

java -jar selenium-server-standalone-2.25.0.jar -role node  -hub http://localhost:4444/grid/register # -nodeConfig Nodes.json
