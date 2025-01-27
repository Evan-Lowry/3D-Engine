WASD to move, mouse to look

General start up loop:
    Scans in map data from obj
        Creates verticies
        Creates UVs
        Creates normals
        Creates triangles
            NOTE: FORMULA TAKEN FROM ONLINE
            Calculates the shading on the triangle based on normals
    Adds data to sectors
    (Only using one, but allows for larger maps in the future)
    Scans in texture files from folder

General game loop:
    Checks for any user input
    Calculates new camera coordinates accordingly
        Checks which floor the camera is on
    Updates all verticies relative to camera position
        Gets all verticies in the active sectors
    
    Draws triangles to screen
        Updates triangles
            Checks triangles for backface culling
            Checks for verticies behind the camera
                Either culls the triangles
                Or adjusts coodinates/UVs that are behind the camera to be in front
        
        Loops through each triangle
            Loops through each pixel the triangle is in
                NOTE: FORMULA TAKEN FROM ONLINE
                Calculates Barycentric coordinates
                Caculates which coresponding texel to use
                Draws pixel to screen