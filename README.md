# Spring_REST_API_Project
Spring REST API project, integrating to database PostgreSQL with some ReactJs for the frontend

The project is build with 3 main generic entities that interacting with each other: User, Elements and Actions:
1) Users - Are devided to 3 roles: PLAYER, MANAGER and ADMIN: 
                    a) PLAYERS - Can view only database that currently exists in the database (and have not been previously deleted).
                    b) MANAGERS - Can add new elements to the database as well as access all the elements, even those who've been deleted.
                    c) ADMINS - Interact with overall data, can export log of actions and delete all users (including admins) and elements from the system.
                    
2) Elements - Are a generic name for possible any actual 'element' for the project. For instance, in a renting systems some possible elements are Apartments and Buildings.
              Both of them would be instances of the ElementBoundary class (as defined in this project) and the way to distinguish between them is through the 'type' attribute:
              For Apartments the attribute will be 'type = Apartment', for buildings the same could be defined 'type = Building'. 
              Then when running querries on elements that're only Apartments (for example) all we'll have to do retrieve all the elements from the database who'se type is 
              set to 'Apartments'.
              
              ** Note - The elements are defined to have a One-To-Many relationship with themselves in a parent-child sort of way (see ElementEntity file), a parent
              element can have multiple children element that are bound to it.

3) Actions - The querries on the elements themselves. The methods are written in the DbElementService file but are accessed from the ActionController

The database is set to be PostgresSQL with the project name as roundMe - both can be changed in the application.properties file.

The React part of the project reflects my initial idea for a renting system so if you're free to completely ignore if it's not relevent for your purposes.
             
                
