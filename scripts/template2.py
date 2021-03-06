########################################################
#
# Copyright (C) 2013 Luca Prete, Andrea Biancini, Fabio Farina - www.garr.it - Consortium GARR
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# 
# author Luca Prete <luca.prete@garr.it>
# author Andrea Biancini <andrea.biancini@garr.it>
# author Fabio Farina <fabio.farina@garr.it>
#
########################################################

from mininet.topo import Topo

class FourSwitches( Topo ):
    "Simple topology example."

    def __init__( self ):
        "Create custom topo."

        # Initialize topology
        Topo.__init__( self )

        # Add hosts and switches
        h1 = self.addHost( 'h1' )
        h2 = self.addHost( 'h2' )

        s1 = self.addSwitch( 's1' )
        s2 = self.addSwitch( 's2' )
        s3 = self.addSwitch( 's3' )
        s4 = self.addSwitch( 's4' )
        s5 = self.addSwitch( 's5' )
        s6 = self.addSwitch( 's6' )
        s7 = self.addSwitch( 's7' )
        s8 = self.addSwitch( 's8' )
        s9 = self.addSwitch( 's9' )
        s10 = self.addSwitch( 's10' )
        s11 = self.addSwitch( 's11' )
        s12 = self.addSwitch( 's12' )
        s13 = self.addSwitch( 's13' )
        s14 = self.addSwitch( 's14' )
        s15 = self.addSwitch( 's15' )
        s16 = self.addSwitch( 's16' )

        # Add links
        self.addLink( s1, h1 )
        self.addLink( s16, h2 )

        self.addLink( s1, s2 )
        self.addLink( s1, s3 )

        self.addLink( s2, s4 )
        self.addLink( s2, s5 )

        self.addLink( s3, s5 )
        self.addLink( s3, s6 )

        self.addLink( s4, s7 )
        self.addLink( s4, s8 )

        self.addLink( s5, s8 )
        self.addLink( s5, s9 )

        self.addLink( s6, s9 )
        self.addLink( s6, s10 )

        self.addLink( s7, s11 )

        self.addLink( s8, s11 )
        self.addLink( s8, s12 )

        self.addLink( s9, s12 )
        self.addLink( s9, s13 )

        self.addLink( s10, s13 )

        self.addLink( s11, s14 )

        self.addLink( s12, s14 )
        self.addLink( s12, s15 )

        self.addLink( s13, s15 )

        self.addLink( s14, s16 )

        self.addLink( s15, s16 )

topos = { 'foursw': ( lambda: FourSwitches() ) }
