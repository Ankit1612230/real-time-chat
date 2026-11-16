import { useEffect, useRef, useState } from 'react'
import { Client } from '@stomp/stompjs'
import SockJS from 'sockjs-client'
import './App.css'

const API = 'http://localhost:8080'
const TEAMS = ['A', 'B']

const addMsg = (list, msg) => (list.some(m => m.id === msg.id) ? list : [...list, msg])
const mergeHistory = (history, live) => [...history, ...live.filter(m => !history.some(h => h.id === m.id))]
const fmt = ts => new Date(ts).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })

function Join({ onJoin }) {
  const [name, setName] = useState('')
  const [team, setTeam] = useState('A')
  return (
    <div className="join">
      <div className="join-card">
        <h1>⚔ ENTER THE ARENA</h1>
        <input
          placeholder="Player name"
          value={name}
          onChange={e => setName(e.target.value)}
          onKeyDown={e => e.key === 'Enter' && name.trim() && onJoin({ name: name.trim(), team })}
        />
        <div className="team-pick">
          {TEAMS.map(t => (
            <button key={t} className={team === t ? 'on' : ''} onClick={() => setTeam(t)}>
              Team {t}
            </button>
          ))}
        </div>
        <button className="go" disabled={!name.trim()} onClick={() => onJoin({ name: name.trim(), team })}>
          JOIN
        </button>
      </div>
    </div>
  )
}

function ChatSidebar({ player }) {
  const [open, setOpen] = useState(true)
  const [tab, setTab] = useState('GLOBAL')
  const [globalMsgs, setGlobalMsgs] = useState([])
  const [teamMsgs, setTeamMsgs] = useState([])
  const [unread, setUnread] = useState({ GLOBAL: 0, TEAM: 0 })
  const [text, setText] = useState('')
  const [connected, setConnected] = useState(false)
  const clientRef = useRef(null)
  const tabRef = useRef(tab)
  const endRef = useRef(null)

  useEffect(() => {
    tabRef.current = tab
    setUnread(u => ({ ...u, [tab]: 0 }))
  }, [tab])

  useEffect(() => {
    fetch(`${API}/api/messages/global`)
      .then(r => r.json())
      .then(h => setGlobalMsgs(l => mergeHistory(h, l)))
      .catch(console.error)
    fetch(`${API}/api/messages/team/${player.team}`)
      .then(r => r.json())
      .then(h => setTeamMsgs(l => mergeHistory(h, l)))
      .catch(console.error)

    const client = new Client({
      webSocketFactory: () => new SockJS(`${API}/chat`),
      reconnectDelay: 3000,
      onConnect: () => {
        setConnected(true)
        client.subscribe('/topic/messages', f => {
          setGlobalMsgs(l => addMsg(l, JSON.parse(f.body)))
          if (tabRef.current !== 'GLOBAL') setUnread(u => ({ ...u, GLOBAL: u.GLOBAL + 1 }))
        })
        client.subscribe(`/topic/team/${player.team}`, f => {
          setTeamMsgs(l => addMsg(l, JSON.parse(f.body)))
          if (tabRef.current !== 'TEAM') setUnread(u => ({ ...u, TEAM: u.TEAM + 1 }))
        })
      },
      onWebSocketClose: () => setConnected(false),
    })
    client.activate()
    clientRef.current = client
    return () => client.deactivate()
  }, [player])

  const messages = tab === 'GLOBAL' ? globalMsgs : teamMsgs

  useEffect(() => {
    endRef.current?.scrollIntoView({ behavior: 'smooth' })
  }, [messages, tab, open])

  const send = () => {
    const content = text.trim()
    if (!content || !connected) return
    const destination = tab === 'GLOBAL' ? '/app/sendMessage' : `/app/team/${player.team}`
    clientRef.current.publish({ destination, body: JSON.stringify({ sender: player.name, content }) })
    setText('')
  }

  return (
    <>
      <button className={`toggle ${open ? 'open' : ''}`} onClick={() => setOpen(o => !o)}>
        {open ? '◀' : '💬'}
      </button>

      <aside className={`sidebar ${open ? 'open' : ''} ${tab.toLowerCase()}`}>
        <header>
          <div>
            <strong>{player.name}</strong>
            <span className="badge">Team {player.team}</span>
          </div>
          <span className={`dot ${connected ? 'on' : ''}`} title={connected ? 'Connected' : 'Offline'} />
        </header>

        <div className="tabs">
          <button className={tab === 'GLOBAL' ? 'active' : ''} onClick={() => setTab('GLOBAL')}>
            🌐 Global {unread.GLOBAL > 0 && <i>{unread.GLOBAL}</i>}
          </button>
          <button className={tab === 'TEAM' ? 'active' : ''} onClick={() => setTab('TEAM')}>
            🛡 Team {player.team} {unread.TEAM > 0 && <i>{unread.TEAM}</i>}
          </button>
        </div>

        <div className="messages">
          {messages.length === 0 && <p className="empty">No messages yet…</p>}
          {messages.map(m => (
            <div key={m.id} className={`msg ${m.sender === player.name ? 'me' : ''}`}>
              <div className="meta">
                <b>{m.sender}</b>
                <span>{fmt(m.timestamp)}</span>
              </div>
              <div className="body">{m.content}</div>
            </div>
          ))}
          <div ref={endRef} />
        </div>

        <footer>
          <input
            value={text}
            placeholder={tab === 'GLOBAL' ? 'Message everyone…' : `Message Team ${player.team}…`}
            onChange={e => setText(e.target.value)}
            onKeyDown={e => e.key === 'Enter' && send()}
          />
          <button onClick={send} disabled={!connected}>SEND</button>
        </footer>
      </aside>
    </>
  )
}

export default function App() {
  const [player, setPlayer] = useState(null)
  return (
    <div className="game">
      <div className="game-hint">🎮 Game world goes here</div>
      {player ? <ChatSidebar player={player} /> : <Join onJoin={setPlayer} />}
    </div>
  )
}